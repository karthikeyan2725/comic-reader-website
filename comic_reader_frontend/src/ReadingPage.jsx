import { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { Link } from 'react-router-dom'
import axios from 'axios'

import ChapterBar from "./ChapterBar"
import Slider from "./common/Slider"
import CommentPanel from "./common/CommentPanel"
import './ReadingPage.css'

function ReadingPage(){
    
    const baseUrl = import.meta.env.VITE_comic_api_url
    const devMode = (import.meta.env.VITE_dev_mode==="true")
    const sliderGenres = ["Action"]
    const imgExtension = ".webp"
    const imgNamePadSize = 3

    const [signedIn, setSignedIn] = useState(false)
    const {chapterId} = useParams();
    const [chapter, setChapter] = useState(null);
    const [imgUrls, setImgUrls] = useState([])
    const [sliderData, setSliderData] = useState({})
    
    async function fetchSliderComics(genre){ 
        try {
            const response = await axios.get(baseUrl + "/comic/comics?genre=" + genre)
            setSliderData(prevData => ({...prevData, [genre] : response.data}))
        } catch (error) {
            console.error("Failed to fetch comics of genre "+ genre + " :" + error.status)
        }
    }

    async function saveToReadHistory(){
        try{
            const response = await axios.post(baseUrl + "/user/read", 
                {token : sessionStorage.getItem("token"), chapterId: chapterId}
            ) 
        } catch (error){
            console.error("Failed to save comic to history : " + error)
        }
    }

    async function getChapter() { 
        try{
            var response = await axios.get(baseUrl + "/chapter/" + chapterId)

            if(!devMode) setChapter(response.data)
            
            if(devMode) {
                var tempChapter = response.data
                tempChapter.chapterLink = "/test"
                tempChapter.pages = 10
                setChapter(response.data)
            }
        } catch (err) {
            console.error("Error fetching chapter details : " + err)
        }
    }

    useEffect(()=>{
        setSignedIn(sessionStorage.getItem("token") != null)
        sliderGenres.forEach((genre)=>{fetchSliderComics(genre)})
    }, [])
    
    useEffect(()=>{
        if(signedIn) saveToReadHistory() 
    }, [signedIn])

    useEffect(()=>{
        if(chapterId != null) {
            getChapter()
            if(signedIn) saveToReadHistory() 
        }
    }, [chapterId])
    
    useEffect(()=>{
        setImgUrls([])
        if(chapter != null) {
            var imgUrls_ = []
            for(var i = 1; i < chapter.pages; i++){
                var imgUrl = chapter.chapterLink + "/" + String(i).padStart(imgNamePadSize, "0") + imgExtension
                imgUrls_.push(imgUrl)
            }

            setImgUrls(imgUrls_)
        }
    }, [chapter])

    return <div className='reading-page'>
        {(chapter != null) ? 
            (<div className = "comic-panel">
                <Link className = 'comic-name' to={"/comic/" + chapter.comicId}>🇬🇧 {chapter.comicName}</Link> {/* TODO: Update language Emoji */}
                <ChapterBar chapter = {chapter}/>
                <div className = 'comic-pages'>
                    {imgUrls.map((imgUrl, i) => 
                        <div key = {i} className = 'page'>
                            <img src = {imgUrl}></img> {/* TODO: Remove unknown space below image */}
                        </div>
                    )}
                </div>
                <ChapterBar chapter = {chapter}/>
            </div>) 
            :
            (<div></div>) // TODO : Add image loading    
        }

        <CommentPanel entityType={"chapter"} entityId={chapterId}></CommentPanel>

        {Object.keys(sliderData).map((key)=>
            <Slider name={key} data ={sliderData[key]}/>
        )}
    </div>
}

export default ReadingPage