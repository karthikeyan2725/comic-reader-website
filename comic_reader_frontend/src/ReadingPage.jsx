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
    </>
}

export default ReadingPage