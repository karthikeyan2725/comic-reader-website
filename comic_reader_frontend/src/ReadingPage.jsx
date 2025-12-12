import { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import axios from 'axios'

import ChapterBar from "./ChapterBar"
import './ReadingPage.css'

function ReadingPage(){
    
    var devMode = false

    const {chapterId} = useParams();

    const[chapter, setChapter] = useState(null);
    const[imgUrls, setImgUrls] = useState([])

    const imgExtension = ".webp"
    const imgNamePadSize = 3

    useEffect(()=>{
        async function getChapter() { // TODO: Refactor again
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
                <h1 className = 'comic-name'>🇬🇧 {chapter.comicName}</h1>
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
    </>
}

export default ReadingPage