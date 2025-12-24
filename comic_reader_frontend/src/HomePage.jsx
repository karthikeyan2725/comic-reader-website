import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"
import axios from "axios"

import Slider from "./common/Slider"
import "./HomePage.css"

function HomePage(){

    const baseUrl = import.meta.env.VITE_comic_api_url
    const devMode = (import.meta.env.VITE_dev_mode==="true")
    const devCoverUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7QNFJ3J1j40v63v45mHPdHN7EE9djaHSEBg&s"
    const sliderGenres = ["Action", "Comedy", "Drama"]
    const hotPanelSpeedInSeconds = 10

    const navigate = useNavigate()
    const [signedIn, setSignedIn] = useState(false)
    const [hotPanel, setHotPanel] = useState({"selected": 0, "comics":[]})
    const [sliderData, setSliderData] = useState({})
    const [readingHistory, setReadingHistory] = useState([]) // TODO: Move to sliderData? Or make new component style for reading list

    function handleHotScroll(event){ // TODO: Wheel event not stopping page scroll, refactor to panel
        event.preventDefault()
        // const numPanel = 4
        // if(event.deltaY > 0 && hotPanel.selected < hotPanel.comics.length-1) setHotPanel(s=>({...s, "selected" : s.selected + 1}))
        // if(event.deltaY < 0 && hotPanel.selected > 0) setHotPanel(s=>({...s, "selected" : s.selected - 1}))
    }

    // useEffect(()=>{
    //     document.getElementsByClassName("hot-panel")[0].onwheel = function(){ return false; }
    // }, [])

    function handleHover(event, i){
        const coverArtDiv = document.getElementById("cover-art-" + i).getBoundingClientRect()
        const x = (event.clientX - coverArtDiv.x) - (coverArtDiv.width/2) 
        const y = (event.clientY - coverArtDiv.y) - (coverArtDiv.height/2)
        const rotationY = 20 * (x/(coverArtDiv.width/2))
        const rotationX = -30 * (y/(coverArtDiv.height/2))
        document.getElementById("cover-art-" + i).style.transform = "perspective(1000px) rotateX(" + rotationX +"deg) rotateY(" + rotationY + "deg)"
    }

    function handleMouseLeave(i){
        document.getElementById("cover-art-" + i).style.transform = ""
    }

    async function fetchPopularComics(){
        try{
            const response = await axios.get(baseUrl + "/comic/popular")
            setHotPanel(p => ({...p, comics : response.data}))
        } catch (error){
            console.error("Failed to fetch popular comics : " + error.status)
        }
    }

    async function fetchSliderComics(genre){ 
        try {
            const response = await axios.get(baseUrl + "/comic/comics?genre=" + genre)
            setSliderData(s => ({...s, [genre] : response.data}))
        } catch (error) {
            console.error("Failed to fetch comics of genre "+ genre + " :" + error.status)
        }
    }

    async function fetchReadingHistory(){
        try {
            const response = await axios.get(baseUrl + "/user/read/history?token=" + sessionStorage.getItem("token"))
            const history = response.data.history
            setReadingHistory(history.sort((a,b) => new Date(b.readTime) - new Date(a.readTime)).map(item => item.comic)) 
        } catch(error){
            console.error("Failed to fetch reading history of user : " + error)
        }
    }

    useEffect(()=>{
        setSignedIn(sessionStorage.getItem("token") != null)
        if(signedIn == true){
            fetchReadingHistory()
        } 
        fetchPopularComics()
        sliderGenres.forEach((genre)=>fetchSliderComics(genre)) // TODO: run in single http request
    }, [signedIn])

    useEffect(()=>{ 
        const hotPanelUpdateTime = hotPanelSpeedInSeconds * 1000
        const interval = setInterval(()=>{setHotPanel(p => ({...p, selected : (p.selected + 1) % hotPanel.comics.length}))}, hotPanelUpdateTime)
        
        return ()=>{clearInterval(interval)}
    }, [hotPanel])
            
    return <div className="home-page">
                
                <div className="hot-panel" onWheel={handleHotScroll}>
                    <div className="hot-panel-items">
                        {hotPanel.comics.map((item, i)=>
                            <div className="hot-panel-item" key={"hot-panel-item" + i}  onClick={()=>{navigate("/comic/" + item.id)}} style={{transform: "translateX(calc(-100% * " + hotPanel.selected +"))"}}> {/* Add Navigation to page on click */}
                                <h1 className="comic-name">{item.name}</h1>
                                <div className="info">
                                    <div className="cover-art" id={"cover-art-" + i} onMouseMove={(event)=>handleHover(event, i)} onMouseLeave={(event)=>handleMouseLeave(i)}>
                                        <img src={(devMode) ? devCoverUrl : item.coverArtUrl}></img>
                                    </div> {/* TODO: Make Image size uniform*/}
                                    <div className="cover-art-spacer"></div>
                                    <div className="genre-description">
                                        <ul className = "genre-list">
                                            {item.genres.map((genre, i)=>
                                                <li><h3 className="genre-bar" key={i}>{genre.name}</h3></li> /* TODO: convert to genre component, also in chapter listis */
                                            )}
                                        </ul>
                                        <h2 className="description">{(item.description)}</h2> {/* TODO: handle text overflow */}
                                    </div>
                                </div>
                            </div>
                        )}
                    </div>
                   
                    <div className="panel-selector">
                        {[...Array(hotPanel.comics.length).keys()].map((n)=>
                            <div className="hot-radio" key = {"selector-panel-circle" + n}> {/* TODO: Unique Key problem*/}
                                <div className={"outer-circle" + ((n == hotPanel.selected) ? " outer-circle-selected " : "")} onClick={()=>{setHotPanel(p => ({...p, "selected" : n}))}}>
                                    <div className="inner-circle"></div>
                                </div>
                            </div>
                        )}
                    </div>
                </div>
                
                {(signedIn && readingHistory.length > 0) ? <Slider name="Continue Reading" data={readingHistory}/> : null}
                
                {Object.keys(sliderData).map((key)=>
                    <Slider name={key} data={sliderData[key]}/>
                )}
             
            </div>
}

export default HomePage