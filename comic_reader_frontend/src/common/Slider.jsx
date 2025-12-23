import { useNavigate } from "react-router-dom"
import { useEffect, useState } from "react"
import { ChevronRight, ChevronLeft } from "lucide-react"
import "./Slider.css"

function Slider({name, data}){ // TODO refactor:

    const devMode = (import.meta.env.VITE_dev_mode==="true")
    const devCoverUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7QNFJ3J1j40v63v45mHPdHN7EE9djaHSEBg&s"
    const sliderItemsSkip = 3

    const navigate = useNavigate()
    const [offset, setOffset] = useState(0)

    return <div className="slider-panel">
            <h1 className="slider-title">{name}</h1>
            <div className="slider-main">
                <ChevronLeft className="slider-button" onClick={(event)=>{if(offset < 0) {setOffset(offset+1)}}}/>
                <div className="slider-items">
                    {data.map((item, i)=>
                        <div className="slider-item" onClick={()=>{navigate("/comic/" + item.id)}} style={{transform: "translateX(" + offset * sliderItemsSkip * 222 +"px)"}}> {/* TODO: Handle slide out from window */}
                            <img className="cover-art"src={(devMode) ? devCoverUrl : item.coverArtUrl}></img>
                            <div className="name-language-row">
                                <h3 className="comic-name">{item.name}</h3>
                                <h3 className="language">🇯🇵</h3> {/* TODO:  Update Language */}
                            </div>
                        </div>
                    )}
                </div>
                <ChevronRight className="slider-button" onClick={(event)=>{if(offset > -data.length) {setOffset(offset-1)}}}/>
            </div>
        </div>
}

export default Slider