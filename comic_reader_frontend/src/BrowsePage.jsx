import { Eye, MessageSquare, Bookmark, StarIcon, Target } from 'lucide-react'
import { useNavigate } from 'react-router-dom'
import { useEffect, useState } from 'react'
import axios from 'axios'

import './BrowsePage.css'

function BrowsePage(){

    const baseUrl = import.meta.env.VITE_comic_api_url

    const devMode = (import.meta.env.VITE_dev_mode==="true") 
    var devCoverUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7QNFJ3J1j40v63v45mHPdHN7EE9djaHSEBg&s"
    
    const navigate = useNavigate()

    const sortBys = ["Comic Name (ASC)", "Comic Name (DESC)", "Publication (DESC)", "Publication (ASC)"]
    const rangeMax = {"after": 1980, "before" : 2025}

    const [searchQuery, setSearchQuery] = useState("")
    const [comics, setComics] = useState([])
    const [filter, setFilter] = useState({
        genre: {"Action": false, "Adventure": false, "Horror": false, "Fantasy":false, "Drama": false, "Comedy": false, "Supernatural": false, "Monster": false, "Coming of age": false, "School Life": false, "Sci-Fi": false},
        sortBy: "",
        publicationRange : {"after": rangeMax.after, "before" : rangeMax.before},
        language: {"English" : false, "Japanese" : false}
    })

    function handleClick(itemType, item){
        if(itemType === "genre" || itemType === "language"){
            setFilter(f => {
                f[itemType][item] = !f[itemType][item]
                return {...f}
            })
        } if(itemType === "sortBy") {
            setFilter(f=>({...f, "sortBy" : item}))
        } if (itemType === "publicationRange"){
            if("event" in item){
                item["event"].preventDefault()
            }
            if("After" in item){
                if(filter.publicationRange.before < item["After"]) return;
                setFilter(f=>{
                    f.publicationRange.after = item["After"]
                    return {...f}
                })
            } if("Before" in item){
                if(filter.publicationRange.after > item["Before"]) return;
                setFilter(f=>{
                    f.publicationRange.before = item["Before"]
                    return {...f}
                })
            } 
        }
    }

    async function fetchComicsAdvanced(){
        try{
            const genreActive = Object.keys(filter.genre).filter((key) => filter.genre[key])
            const languageActive = Object.keys(filter.language).filter((key) => filter.language[key])

            const response = await axios.get(baseUrl + "/comic/advanced-search",
                {
                    params: {
                    "query": searchQuery,
                    "genres" : genreActive,
                    "sortBy": filter.sortBy,
                    "before" : filter.publicationRange.before,
                    "after" : filter.publicationRange.after,
                    "languages" : languageActive
                }}
            )
            setComics(response.data)
        } catch(error){
            console.error("Failed to perform advanced search : " + error)
        }
    }

    useEffect(()=>{
        fetchComicsAdvanced()
    },[])

    return <div className='browse-page'>
            <div className='filter-panel'>

                <h4 className='filter-title'>Genres</h4> {/* TODO: Move to component */}
                <ul className='filter-genres'>
                    {Object.keys(filter.genre).map((item, i)=>
                        <li key={item} className='filter-list-item' onClick={()=>handleClick("genre", item)}>
                            <div>{item}</div> 
                            <div className={'filter-checkbox ' + ((filter.genre[item]) ? 'filter-checkbox-selected' : '')} type='checkbox'></div>
                        </li>
                    )}
                </ul>

                <h4 className='filter-title'>Sort By</h4> {/* TODO: Move to component */}
                <ul className='sort-by'>
                    {sortBys.map((item, i)=>
                        <li className='filter-list-item' onClick={()=>{handleClick("sortBy", item)}}>
                            <div>{item}</div> 
                            <div className={'filter-checkbox ' + ((filter.sortBy === item) ? 'filter-checkbox-selected' : '')} type='checkbox'></div>
                        </li>
                    )}
                </ul>

                <h4 className='filter-title'>Publication Range</h4> {/* TODO: Move to component */}
                <div className='filter-year'>  {/*TODO: Make custom 2 handle slider*/}
                    After: {filter.publicationRange.after}
                    <input type="range" min={rangeMax.after} max={rangeMax.before} value={filter.publicationRange.after} onChange={(event)=>{handleClick("publicationRange", {"After": event.target.value, "event" : event})}}></input> {/* TODO: Merge event into one */}
                    Before: {filter.publicationRange.before}
                    <input type="range" min={rangeMax.after} max={rangeMax.before} value={filter.publicationRange.before} onChange={(event)=>{handleClick("publicationRange", {"Before": event.target.value, "event" : event})}}></input>
                </div>

                <h4 className='filter-title'>Language</h4>  {/* TODO: Move to component */}
                <ul className='languages'>
                    {Object.keys(filter.language).map((item)=>
                        <li className='filter-list-item' onClick={()=>{handleClick("language", item)}}>
                            <div>{item}</div> 
                            <div className={'filter-checkbox ' + ((filter.language[item]) ? 'filter-checkbox-selected' : '')} type='checkbox'></div>
                        </li>
                    )}
                </ul>
            </div>

            <div className="search-panel">
                <div className='advanced-search-bar'> {/* TODO : Make this the bar...*/ }
                    <input className="search-bar" type="text" placeholder='Type Here to search...' value={searchQuery} onChange={(event)=>setSearchQuery(event.target.value)} onKeyDown={(event)=>{(event.key == "Enter")? fetchComicsAdvanced() : null}}/>
                    <button className="search-button" onClick={()=>{fetchComicsAdvanced()}}>Search</button>
                </div>

                <div className="search-result-container">

                    {(comics.length != 0) ? comics.map((item)=>
                        <div className='search-item' onClick={()=>navigate("/comic/" + item.id)}> 
                            <div className="search-item-body">
                                <div className='info-row'>
                                    <img className='cover-art' src={(devMode) ? devCoverUrl : item.coverArtUrl}></img>
                                    <div className='info-text'>
                                        <h2 className='comic-name'>{item.name}</h2>
                                        <p className='description'>{item.description}</p>
                                    </div>
                                </div>
                                <div className='genre-row'>
                                    <ul className='genre-list'>
                                        {item["genres"].map((genreItem)=>
                                            <li className='genre-item'>{genreItem.name}</li>
                                        )}
                                    </ul>
                                    <div className='more-genre'>more...</div> {/* TODO: Add onClick */}
                                </div>
                                <ul className='metrics-row'> {/* TODO: Move to Single object and render each element? */}
                                    <li className='metric-item'>
                                        <Eye className='icon'/> 
                                        <div className='value'>100k</div>
                                    </li>
                                    <li className='metric-item'>
                                        <Bookmark className='icon'/>
                                        <div className='value'>100k</div>
                                    </li>
                                    <li className='metric-item'>
                                        <StarIcon className='icon'/> 
                                        <div className='value'>100k</div>
                                    </li>
                                    <li className='metric-item'>
                                        <MessageSquare className='icon'/> 
                                        <div className='value'>100k</div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    ) 
                    : 
                    <div className='no-search-result'>
                        <img src="https://static.vecteezy.com/system/resources/thumbnails/031/098/165/small/pixel-art-cartoon-rubber-duck-icon-png.png"></img>
                        <h2>Sorry. Cant find a match :(</h2>
                    </div>}
                </div>
            </div>
        </div>
}

export default BrowsePage