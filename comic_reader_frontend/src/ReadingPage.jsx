import { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom';
import './ReadingPage.css'
import axios from 'axios';

function ReadingPage(){
    
    const {chapterId} = useParams();

    const[chapter, setChapter] = useState(null);
    const[message, setMessage] = useState(null);
    const[items, setItems] = useState([])
    
    useEffect(()=>{
        if(chapter != null) {
            var newItems = []
            for(var i = 1; i < chapter.pages; i++){
                // var imgUrl = "https://vnqpkstmadacbnlcvgax.supabase.co/storage/v1/object/public/comic-asia/120345/" + i + ".jpg"
                // var imgUrl = "https://i.pinimg.com/736x/23/ea/4a/23ea4acfdd48c812398006c3d754aa78.jpg"
                var imgUrl = chapter.chapterLink + "/" + String(i).padStart(3, "0") + ".webp"
                newItems.push(<div key = {i} className = 'page'>
                        <img src = {imgUrl}></img>
                    </div>)
            }
            setItems(newItems)
            console.log(chapter)
        }
    }, [chapter])
    

    async function getChapter() {
        try{
            var response = await axios.get("http://localhost:8080/chapter/" + chapterId)
            setChapter(response.data)
        } catch (err) {
            console.log("Error fetching chapter details : " + err)
        }
    }

    useEffect(()=>{
        getChapter()
    }, [])


    return <>
        <div className = 'header'>
            <div className = 'logo'>ComicRaven</div>
            <nav className = 'links'> 
                <ul>
                    <li><a>Home</a></li>
                    <li><a>Browse</a></li>
                </ul>
            </nav>
            <input className = 'search' type='text' placeholder='Search'></input>
            <div className = 'profile'>P</div>
        </div>

        {(chapter != null) ? 
            (<div className = "comic-panel">
                <h1 className = 'comic-name'>🇬🇧 {chapter.comicName}</h1>

                 <div className = "chapter-bar">
                    <a href={ (chapter.prevChapterId != null) ?'/chapter/' + chapter.prevChapterId : ''}>&lt; prev</a> 
                    <a>Chapter {(chapter != null) ? chapter.chapterNumber : "X"}</a> 
                    <a href={ (chapter.nextChapterId != null) ? '/chapter/' + chapter.nextChapterId : ''}>next &gt;</a> 
                </div>

                <div className = 'comic-pages'>
                    {items}
                </div>

                <div className = "chapter-bar">
                    <a href={ (chapter.prevChapterId != null) ?'/chapter/' + chapter.prevChapterId : ''}>&lt; prev</a> 
                    <a>Chapter {(chapter != null) ? chapter.chapterNumber : "X"}</a> 
                    <a href={ (chapter.nextChapterId != null) ? '/chapter/' + chapter.nextChapterId : ''}>next &gt;</a> 
                </div>

                <div className = "footer">
                    
                    <div className = "links">
                        GitHub | Buy Me A Coffee | DMCA | Report
                    </div>
                    
                    <div className = "copyright">
                        © 2025 ComicRaven.com
                    </div>
                </div>
            </div>) 
            :
            (<div></div>)      
        }
    </>
    
}

export default ReadingPage