import { Link, useParams } from "react-router-dom"
import { useEffect, useState } from "react"
import axios from "axios"

import computePublishedAgoString from "./Util"
import CommentPanel from "./common/CommentPanel"
import "./ChapterListingPage.css"

function ChapterListingPage(){

    const baseUrl = import.meta.env.VITE_comic_api_url

    const devMode = (import.meta.env.VITE_dev_mode==="true")
    var devCoverUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7QNFJ3J1j40v63v45mHPdHN7EE9djaHSEBg&s"
    
    const {comicId} = useParams()

    const [comic, setComic] = useState(null)

    async function getComicDetails(){
        try {
            const response = await axios.get(baseUrl + "/comic/" + comicId)

            setComic(response.data)
        }
        catch (error) {
            console.error("Failed to retrieve Comic Details for " + comicId)
        }
    }

    useEffect(()=>{
        if(comicId != null) {
            getComicDetails()
        }
    }, [comicId])

    return (comic != null) ?
        <div className = "comic-listing-page">
            <div className = "comic-details">
                <img className = "cover-art" src = {devMode ? devCoverUrl : comic.coverArtUrl}></img>
                <div className = "text-pane">
                    <h1 className = "comic-name">{comic.name} 🇬🇧</h1> {/* TODO: Handle Language Icon */}
                    <ul className = "staff-list">
                        <li><h3 className = "comic-staff">Author: {comic.author}</h3></li>
                        <li><h3 className = "comic-staff">Artist: {comic.artist}</h3></li>
                    </ul>
                    <ul className = "genre-list">
                        {comic.genres.map((genre, i)=>
                            <li><h3 className="genre-bar" key={i}>{genre.name}</h3></li>
                        )}
                    </ul>
                    <h2 className = "description">{comic.description}</h2> 
                </div>
            </div>
            <div className = "chapter-list-panel">
                <h2>[Chapters]</h2>
                <ul className = "chapter-list">
                    {comic.chapters.sort((a, b) => a.chapterNumber - b.chapterNumber).map((chapter, i) => 
                        <li className = "chapter-item" key = {i}>
                            <Link className = "chapter-link" to={"/chapter/" + chapter.id} key = {i}> 
                                <div className="chapter-num">Chapter {chapter.chapterNumber}</div> 
                                <div className="read-published">Reads : {chapter.readCount} - Published : {computePublishedAgoString(chapter.publishedTime)}</div>
                            </Link>
                        </li>
                    )}
                </ul>
            </div>
            
            <CommentPanel entityType={"comic"} entityId={comicId}/>
        </div>
        :
        <div></div>
}

export default ChapterListingPage