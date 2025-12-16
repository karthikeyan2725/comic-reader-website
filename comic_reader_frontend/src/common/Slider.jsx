import { useNavigate } from "react-router-dom"
import "./Slider.css"

function Slider({name, data}){ // TODO refactor:

    var devMode = false
    const devCoverUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7QNFJ3J1j40v63v45mHPdHN7EE9djaHSEBg&s"

    const navigate = useNavigate()

    return <div className="slider-panel">
            <h1 className="slider-title">{name}</h1>
            <div className="slider-items">
                {data.map((item, i)=>
                    <div className="slider-item" onClick={()=>{navigate("/comic/" + item.id)}}>
                        <img className="cover-art"src={(devMode) ? devCoverUrl : item.coverArtUrl}></img>
                        <div className="name-language-row">
                            <h3 className="comic-name">{item.name}</h3>
                            <h3 className="language">🇯🇵</h3> {/* TODO:  Update Language */}
                        </div>
                    </div>
                )}
            </div>
        </div>
}

export default Slider