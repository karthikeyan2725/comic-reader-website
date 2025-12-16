import { useEffect, useState } from "react"

import "./HomePage.css"

function HomePage(){

    const devCoverUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7QNFJ3J1j40v63v45mHPdHN7EE9djaHSEBg&s"
    const devComicGenres = [{"name":"Gore"},{"name":"Monster"},{"name":"Post Apocalyptic"},{"name":"Friendship"},{"name":"Hardwork"}]
    const devComicDescription = "The story follows Kafka Hibino who, after ingesting a parasitic creature, gains the ability to turn into a kaiju and now must navigate using his power while trying to become part of an organization that eliminates kaiju to fulfill a promise he made with a childhood friend. Matsumoto wrote the outline of the story of Kaiju No. 8 near the end of 2018 making it his second series for the magazine. The series was heavily influenced by Japanese tokusatsu media, especially Ultraman, while the author's struggles in the manga industry served as a basis for the main character's backstory."
    
    const [panelNum, setPanelNum] = useState(0)
    
    function handleHotScroll(event){
        const numPanel = 4
        if(event.deltaY > 0 && panelNum < numPanel) setPanelNum(panelNum + 1)
        if(event.deltaY < 0 && panelNum > 0) setPanelNum(panelNum - 1)            
    }

    useEffect(()=>{
        const interval = setInterval(()=>{setPanelNum(((panelNum + 1) % 5))}, 5000)
        return ()=>{clearInterval(interval)}
    })
            
    return <div className="home-page">
                <div className="hot-panel" onWheel={handleHotScroll}>
                    <div  className="hot-panel-items">
                        {[0,1,2,3,4].map((item, i)=>
                            <div className="hot-panel-item" key={i} style={{transform: "translateX(calc(-100% * " + panelNum +"))"}}>
                                <h1 className="comic-name">{"Kaiju No 8"}</h1>
                                <div className="info">
                                    <img className="cover-art" src={devCoverUrl}></img>
                                    <div className="cover-art-spacer"></div>
                                    <div className="genre-description">
                                        <ul className = "genre-list">
                                            {devComicGenres.map((genre, i)=>
                                                <li><h3 className="genre-bar" key={i}>{genre.name}</h3></li> /* TODO: convert to genre component, also in chapter listis */
                                            )}
                                        </ul>
                                        <h2 className="description">{devComicDescription}</h2>
                                    </div>
                                </div>
                            </div>
                        )}
                    </div>
                   
                    <div className="panel-selector">
                        {[0,1,2,3,4].map((n, i)=>
                            <div className="hot-radio"> {/* Unique Key problem*/}
                                <div className={"outer-circle" + ((n == panelNum) ? " outer-circle-selected " : "")} onClick={()=>{setPanelNum(n)}}>
                                    <div className="inner-circle"></div>
                                </div>
                            </div>
                        )}
                    </div>
                </div>

                <div className="">

                </div>
            </div>
}

export default HomePage;