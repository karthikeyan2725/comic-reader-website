import { useEffect, useState } from "react"
import axios from "axios"

import computePublishedAgoString from "../Util"
import "./CommentPanel.css"

function CommentPanel({entityType, entityId}){
    
    const baseUrl = import.meta.env.VITE_comic_api_url

    const [comments, setComments] = useState([])
    const [comment, setComment] = useState("")

    async function postCommentVote(commentId, vote){
        try{
            const token = sessionStorage.getItem("token")
            if(token == null) return;
            const response = await axios.post(baseUrl + "/" + entityType + "/comment/" + commentId + "/vote?token=" + token + "&vote=" + vote)
            getComments()
        } catch(error){
            console.error("Failed to vote comment " + commentId + " : " + error)
        }
    }

    async function getComments(){
        try{
            var response;
            if(sessionStorage.getItem("token") == null)
                response = await axios.get(baseUrl  + "/" + entityType + "/" + entityId + "/comments")
            else 
                response = await axios.get(baseUrl  + "/" + entityType + "/" + entityId + "/comments?token=" + sessionStorage.getItem("token"))
            
            setComments(response.data.sort((c1, c2) => new Date(c1.publishedTime) - new Date(c2.publishedTime)))
        } catch (error) {
            console.error("Failed to retrieve comments for Comic Id : " + entityId + " Error " + error)
        }
    }

    async function postComments(){
        try {
            const response = await axios.post(baseUrl + "/" + entityType + "/comment", 
                {"token": sessionStorage.getItem("token"),
                "commentType" : entityType,
                "commentEntityId" : entityId,
                "comment" : comment}
            )

            setComment("")
            getComments()
        } catch (error) {
            console.error("Failed to post comment of user at end point " + " Error: " + error)
        }
    }

    useEffect(()=>{
        if(entityId != null && entityType != null) getComments()
    }, [entityId, entityType])

    return <div className="comments-panel">
                <h2 className="panel-header">Comments</h2>

                <ul className="comments-list">
                        { ( comments.length > 0) ?
                            comments.map((commentItem, i) => 
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
                                            <h3 className="upvote-num" style={(commentItem.commentVote) ? ((commentItem.commentVote.vote == -1) ? ({color:"red"}) : ({color:"green"}) ) : ({color:"white"})}>{commentItem.votes}</h3> {/* TODO: Refactor Inline js*/}
                                            <h3 className="vote-buttons">
                                                <div className="vote" onClick={()=>postCommentVote(commentItem.id, 1)} style={{textDecoration : (commentItem.commentVote != null && commentItem.commentVote.vote==1)?"underline":"none"}}>Upvote</div> | <div className="vote" onClick={()=>postCommentVote(commentItem.id, -1)} style={{textDecoration : (commentItem.commentVote != null && commentItem.commentVote.vote==-1)?"underline":"none"}}>Downvote</div>
                                            </h3>
                                        </div>
                                    </div>
                                    <h3 className="comment-text">{commentItem.comment}</h3>
                                </li>)
                            :
                            null
                        }
                </ul>
                    
                <div className="comment-bar">
                    <div className="profile-comment-row">
                        <img className="profile-pic" src="https://www.shutterstock.com/image-vector/vector-profile-icon-260nw-380603071.jpg"></img>
                        <input className="comment-textfield" type="text" placeholder={(sessionStorage.getItem("token") != null) ? "Enter Your comment..." : "Sign In To comment"} disabled = {(sessionStorage.getItem("token") == null)} value = {comment} onChange={(event)=>{setComment(event.target.value)}}/>
                    </div>
                    {(sessionStorage.getItem("token") != null) ?
                            <div className="comment-cancel-row">
                            <div className="cancel" onClick={()=>{setComment("")}}>Cancel</div> {/* Cancel -> clear */}
                            <div className="comment" onClick={()=>{postComments()}}>Comment</div>
                        </div>
                        :
                        null
                    }
                </div>
        </div>
}

export default CommentPanel