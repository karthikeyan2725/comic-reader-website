import { Link } from "react-router-dom"

import "./ChapterBar.css"

function ChapterBar({chapter}){
    return <div className = "chapter-bar">
        <Link to={ (chapter.prevChapterId != null) ?'/chapter/' + chapter.prevChapterId : ''} className="link">&lt; prev</Link> 
        <div>Chapter {(chapter != null) ? chapter.chapterNumber : "X"}</div> 
        <Link to={ (chapter.nextChapterId != null) ? '/chapter/' + chapter.nextChapterId : ''} className="link">next &gt;</Link> 
    </div>
}

export default ChapterBar