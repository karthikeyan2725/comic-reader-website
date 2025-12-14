import { Link, useParams } from "react-router-dom"
import { useEffect, useState } from "react"
import axios from "axios"

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
            // console.log("Response")
            // console.log(response)
            setComic(response.data)
        }
        catch (error) {
            console.error("Failed to retrieve Comic Details for " + comicId + " " + error)
        }
    }

    async function getComicComments(){
        try{
            const response = await axios.get("http://localhost:8080/comic/" + comicId + "/comments")
            setComments(response.data)
            console.log(response.data)
        } catch {
            console.error("Failed to retrieve comments for Comic Id : " + comicId)
        }
    }

    async function postComicComment(){
        try {
            const response = await axios.post("http://localhost:8080/comic/comment", // TODO: change API 
                {"user" : {"id" : 1},  // TODO: Get UserId from JWT or Session
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

    function computePublishedAgoString(time){
        var days = Math.floor((Date.now() - new Date(time)) / (60 * 60 * 24 * 365))
        if(days == 0) return "Today"
        if(days == 1) return days + " day ago"
        return days + " days ago"
    }

    return (comic != null) ?
        <div className = "comic-listing-page">
            <div className = "comic-details">
                <img className = "cover-art" src = {devMode ? devCoverUrl : comic.coverArtUrl}></img>
                <div className = "text-pane">
                    <h1 className = "comic-name">{comic.name} 🇬🇧</h1> {/* TODO: Handle Language Icon */}
                    <ul className = "staff-list">
                        <li><h4 className = "comic-staff">Author: {comic.author}</h4></li>
                        <li><h4 className = "comic-staff">Artist: {comic.artist}</h4></li>
                    </ul>
                    <ul className = "genre-list">
                        {comic.genres.map((genre, i)=>
                            <li><h4 className="genre-bar" key={i}>{genre.name}</h4></li>
                        )}
                    </ul>
                    <h2 className = "description">{comic.description}</h2> {/* TODO: Make this p? */}
                </div>
            </div>
            <div className = "chapter-list-panel">
                <h2>[Chapters]</h2>
                {/*TODO: Add space between chapter number and (reads and published : ), sort options*/}
                <ul className = "chapter-list">
                    {comic.chapters.sort((a, b) => a.chapterNumber - b.chapterNumber).map((chapter, i) => 
                        <li className = "chapter-item" key = {i}>
                            <Link className = "chapter-link" to={"/chapter/" + chapter.id} key = {i}> Chapter {chapter.chapterNumber}, Reads : {chapter.readCount}, Published : {computePublishedAgoString(chapter.publishedTime)} </Link>
                        </li>
                    )}
                </ul>
            </div>
            
            <div className="comments-panel">
                    <h2 className="panel-header">Comments</h2>

                    <ul className="comments-list">
                            {comments.map((commentItem, i) => 
                                <li key={i} className="comment-item"> {/* TODO: Key prop not applied? */}
                                    <div className="profile-vote-row">
                                        <div className="profile">
                                            <img className="profile-pic" src = "https://www.shutterstock.com/image-vector/vector-profile-icon-260nw-380603071.jpg"></img>
                                            <div className="name-time-section">
                                                <h3 className="name">{commentItem.user.email}</h3>
                                                <h5 className="comment-time">{computePublishedAgoString(commentItem.publishedTime)}</h5>
                                            </div>
                                        </div>
                                        <div className="vote-section">
                                            <h3 className="upvote-num">{commentItem.upvote}</h3>
                                            <h3 className="vote-buttons">
                                                <div className="vote" onClick={()=>{console.log("Upvote")}}>Upvote</div> | <div className="vote" onClick={()=>(console.log("downvote"))}>Downvote</div>
                                            </h3>
                                        </div>
                                    </div>
                                    <h3 className="comment-text">{commentItem.comment}</h3>
                                </li>
                            )}
                    </ul>
                      
                    <div className="comment-bar">
                        <div className="profile-comment-row">
                            <img className="profile-pic" src="https://www.shutterstock.com/image-vector/vector-profile-icon-260nw-380603071.jpg"></img>
                            <input className="comment-textfield" type="text" placeholder={(sessionStorage.getItem("token") != null) ? "Enter Your comment..." : "Sign In To comment"} disabled = {(sessionStorage.getItem("token") == null)} value = {comment} onChange={(event)=>{setComment(event.target.value)}}/>
                        </div>
                        {(sessionStorage.getItem("token") != null) ?
                             <div className="comment-cancel-row">
                                <div className="cancel" onClick={()=>{setComment("")}}>Cancel</div> {/* Cancel -> clear */}
                                <div className="comment" onClick={()=>{postComicComment()}}>Comment</div>
                            </div>
                            :
                            null
                        }
                    </div>
            </div>
        </div>
        :
        <div></div>
}

export default ChapterListingPage