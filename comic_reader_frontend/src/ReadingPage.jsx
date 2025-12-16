import { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { Link } from 'react-router-dom'
import axios from 'axios'

import ChapterBar from "./ChapterBar"
import Slider from "./common/Slider"
import './ReadingPage.css'

function ReadingPage(){
    
    var devMode = true
    const devCoverUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7QNFJ3J1j40v63v45mHPdHN7EE9djaHSEBg&s"

    const {chapterId} = useParams();

    const [chapter, setChapter] = useState(null);
    const [imgUrls, setImgUrls] = useState([])

    const [sliderData, setSliderData] = useState({})
    const sliderGenres = ["Action"]

    const imgExtension = ".webp"
    const imgNamePadSize = 3

    async function fetchSliderComics(genre){ 
        try {
            const response = await axios.get("http://localhost:8080/comic/comics?genre=" + genre)
            setSliderData(prevData => ({...prevData, [genre] : response.data}))
        } catch (error) {
            console.error("Failed to fetch comics of genre "+ genre + " :" + error.status)
        }
    }

    useEffect(()=>{
        sliderGenres.forEach((genre)=>{fetchSliderComics(genre)})
    }, [])

    async function getChapter() { 
        try{
            var response = await axios.get("http://localhost:8080/chapter/" + chapterId)

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
        if(chapterId != null){
            getChapter()
            setImgUrls([])
        }
    }, [chapterId])

    useEffect(()=>{
        if(chapter != null) {
            var imgUrls_ = []
            for(var i = 1; i < chapter.pages; i++){
                var imgUrl = chapter.chapterLink + "/" + String(i).padStart(imgNamePadSize, "0") + imgExtension
                imgUrls_.push(imgUrl)
            }

            setImgUrls(imgUrls_)
        }
    }, [chapter])

    return <>
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

        {Object.keys(sliderData).map((key)=>
            <Slider name={key} data ={sliderData[key]}/>
        )}
    </>
}

export default ReadingPage