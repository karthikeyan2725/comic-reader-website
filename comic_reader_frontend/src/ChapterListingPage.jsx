import { Link, useParams } from "react-router-dom"
import { useEffect, useState } from "react"
import axios from "axios"

import "./ChapterListingPage.css"

function ChapterListingPage(){

    const {comicId} = useParams()

    const [comic, setComic] = useState(null)

    async function getComicDetails(){
        try {
            const response = await axios.get("http://localhost:8080/comic/" + comicId)
            console.log("Response")
            console.log(response)
            setComic(response.data)
        }
        catch (error) {
            console.error("Failed to retrieve Comic Details for " + comicId + " " + error)
        }
    }
    
    useEffect(()=>{
        if(comicId != null) getComicDetails()
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
                <img className = "cover-art" src = {comic.coverArtUrl}></img>
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
        </div>
        :
        <div></div>
}

export default ChapterListingPage