import { Link, useParams } from "react-router-dom"
import { useEffect, useState } from "react"
import axios from "axios"

import computePublishedAgoString from "./Util"
import CommentPanel from "./common/CommentPanel"
import "./ChapterListingPage.css"

function ChapterListingPage(){

    var devMode = true
    var devCoverUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7QNFJ3J1j40v63v45mHPdHN7EE9djaHSEBg&s"
    
    const {comicId} = useParams()

    const [comic, setComic] = useState(null)
    const [comments, setComments] = useState([])
    const [comment, setComment] = useState("")

    async function getComicDetails(){
        try {
            const response = await axios.get("http://localhost:8080/comic/" + comicId)

            setComic(response.data)
        }
        catch (error) {
            console.error("Failed to retrieve Comic Details for " + comicId)
        }
    }

    async function getComicComments(){
        try{
            const response = await axios.get("http://localhost:8080/comic/" + comicId + "/comments")

            setComments(response.data)
        } catch {
            console.error("Failed to retrieve comments for Comic Id : " + comicId)
        }
    }

    async function postComicComment(){
        try {
            const response = await axios.post("http://localhost:8080/comic/comment", 
                {"token": sessionStorage.getItem("token"),
                "commentType" : "comic",
                "commentEntityId" : comicId,
                "comment" : comment}
            )

            setComment("")
            getComicComments()
        } catch (err) {
            console.error("Failed to post comment of user")
        }
    }

    useEffect(()=>{
        if(comicId != null) {
            getComicDetails()
            getComicComments()
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