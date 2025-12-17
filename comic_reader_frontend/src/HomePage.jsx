import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom";
import axios from "axios"

import Slider from "./common/Slider";
import "./HomePage.css"

function HomePage(){

    const baseUrl = import.meta.env.VITE_comic_api_url

    const devMode = true
    const devCoverUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7QNFJ3J1j40v63v45mHPdHN7EE9djaHSEBg&s"

    const navigate = useNavigate()

    const [panelNum, setPanelNum] = useState(0)
    const [popularComics, setPopularComics] = useState([])

    const [sliderData, setSliderData] = useState({})
    const sliderGenres = ["Action", "Comedy", "Post Apocalyptic"]

    function handleHotScroll(event){ // TODO: Wheel event not stopping page scroll
        // event.preventDefault()
        // const numPanel = 4
        // if(event.deltaY > 0 && panelNum < numPanel) setPanelNum(panelNum + 1)
        // if(event.deltaY < 0 && panelNum > 0) setPanelNum(panelNum - 1)
    }

    async function fetchPopularComics(){
        try{
            const response = await axios.get(baseUrl + "/comic/popular")
            setPopularComics(response.data)
        } catch (error){
            console.error("Failed to fetch popular comics : " + error.status)
        }
    }

    async function fetchSliderComics(genre){ 
        try {
            const response = await axios.get(baseUrl + "/comic/comics?genre=" + genre)
            setSliderData(prevData => ({...prevData, [genre] : response.data}))
        } catch (error) {
            console.error("Failed to fetch comics of genre "+ genre + " :" + error.status)
        }
    }

    useEffect(()=>{
        fetchPopularComics()
        sliderGenres.forEach((genre)=>{fetchSliderComics(genre)})
    }, [])

    useEffect(()=>{
        const interval = setInterval(()=>{setPanelNum(((panelNum + 1) % 5))}, 10000)
        return ()=>{clearInterval(interval)}
    })
            
    return <div className="home-page">
                
                <div className="hot-panel" onWheel={handleHotScroll}>
                    <div className="hot-panel-items">
                        {popularComics.map((item, i)=>
                            <div className="hot-panel-item" key={"hot-panel-item" + i}  onClick={()=>{navigate("/comic/" + item.id)}} style={{transform: "translateX(calc(-100% * " + panelNum +"))"}}> {/* Add Navigation to page on click */}
                                <h1 className="comic-name">{item.name}</h1>
                                <div className="info">
                                    <img className="cover-art" src={(devMode) ? devCoverUrl : item.coverArtUrl}></img> {/* TODO: Make Image size uniform*/}
                                    <div className="cover-art-spacer"></div>
                                    <div className="genre-description">
                                        <ul className = "genre-list">
                                            {item.genres.map((genre, i)=>
                                                <li><h3 className="genre-bar" key={i}>{genre.name}</h3></li> /* TODO: convert to genre component, also in chapter listis */
                                            )}
                                        </ul>
                                        <h2 className="description">{item.description}</h2> {/* TODO: handle text overflow */}
                                    </div>
                                </div>
                            </div>
                        )}
                    </div>
                   
                    <div className="panel-selector">
                        {[...Array(popularComics.length).keys()].map((n)=>
                            <div className="hot-radio" key = {"selector-panel-circle" + n}> {/* TODO: Unique Key problem*/}
                                <div className={"outer-circle" + ((n == panelNum) ? " outer-circle-selected " : "")} onClick={()=>{setPanelNum(n)}}>
                                    <div className="inner-circle"></div>
                                </div>
                            </div>
                        )}
                    </div>
                </div>

                {Object.keys(sliderData).map((key)=>
                    <Slider name={key} data={sliderData[key]}/>
                )}
             
            </div>
}

export default HomePage;