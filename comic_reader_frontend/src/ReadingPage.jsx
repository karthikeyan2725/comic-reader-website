import './ReadingPage.css'

function ReadingPage(){
    return <>

        <div class = 'header'>
            <div class = 'logo'>ComicRaven</div>
            <nav class = 'links'> 
                <ul>
                    <li><a>Home</a></li>
                    <li><a>Browse</a></li>
                </ul>
            </nav>
            <input class = 'search' type='text' placeholder='Search'></input>
            <div class = 'profile'>P</div>
        </div>

        <div class = "comic-panel">

            <h1 class = 'comic-name'>🇬🇧 Bakuman</h1>

            <div class = "chapter-bar">
                <a>&lt; prev</a> 
                <a>Chapter</a> 
                <a>next &gt;</a> 
            </div>

            <div class = 'comic-pages'>
                <div class = 'page'>
                    <img src = "https://wallpapers.com/images/hd/manga-pages-yjxwq1kmwmmeg1d6.jpg"></img>
                </div>
                <div class = 'page'>
                    <img src = "https://wallpapers.com/images/hd/manga-pages-yjxwq1kmwmmeg1d6.jpg"></img>
                </div>
                <div class = 'page'>
                    <img src = "https://wallpapers.com/images/hd/manga-pages-yjxwq1kmwmmeg1d6.jpg"></img>
                </div>
            </div>

            <div class = "chapter-bar">
                <a>&lt; prev</a> 
                <a>Chapter</a> 
                <a>next &gt;</a> 
            </div>

            <div class = "footer">
                
                <div class = "links">
                    GitHub  | Buy Me A Coffee | DMCA | Report
                </div>
                
                <div class = "copyright">
                    © 2025 ComicRaven.com
                </div>
            </div>
        </div>
    </>
}

export default ReadingPage