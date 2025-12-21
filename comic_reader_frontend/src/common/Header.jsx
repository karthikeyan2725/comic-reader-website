import { Link, useNavigate } from "react-router-dom"
import axios from "axios"

import "./Header.css"
import { useEffect, useState } from "react"

function Header(){ // TODO: Refactor header

    const baseUrl = import.meta.env.VITE_comic_api_url

    const navigate = useNavigate()
    const [signedIn, setSignedIn] = useState(false)
    const [profileActive, setProfileActive] = useState(false)
    const [searchActive, setSearchActive] = useState(false) // TODO: Move the 3 "search" things to single state object
    const [searchQuery, setSearchQuery] = useState("")
    const [searchContent, setSearchContent] = useState([])
    
    async function searchComic(searchQuery){
        if(searchQuery == "") return setSearchContent([])

        try{
            const response = await axios.get(baseUrl + "/comic/search?query=" + searchQuery)
            setSearchContent(response.data)
        } catch(error){
            console.error("Failed to execute search : " + error)
        }
    }

    useEffect(()=>{
        setSignedIn(sessionStorage.getItem("token") != null)
    }, [])

    useEffect(()=>{
        searchComic(searchQuery)
    }, [searchQuery])
    
    return <div className = 'header'>
            <div className = 'logo'>ComicRaven</div>
            <nav className = 'links'> 
                <ul>
                    <li><Link className = "link" to="/" onClick={()=>setSearchQuery("")}>Home</Link></li> 
                    <li><Link className = "link" onClick={()=>setSearchQuery("")}>Browse</Link></li>
                </ul>
            </nav>
            <div className="search"> {/* TODO: make outside click only to disable search */}
                <input className = 'bar' type='text' placeholder='Search' onClick={()=>{setSearchActive(true)}} onChange={(event)=>{setSearchQuery(event.target.value)}} value={searchQuery}></input>
                {(searchActive) ? 
                    <ul className="search-items">
                        {searchContent.map((item)=>
                            <li className="search-item" onClick={()=>{setSearchQuery(""); navigate("comic/" + item.id)}}>{item.name}</li>
                        )}
                    </ul>
                    : null}
            </div>
            <div className = 'profile' to={(signedIn) ? null : "/sign-in" } onClick={()=>{(signedIn) ? setProfileActive(!profileActive) : navigate("/sign-in");}}>
                <h4>{(signedIn) ? "P" : "U"}</h4>
                <div className="profile-drop-down" style={{"display": (profileActive) ? "block" : "none"}} onClick={()=>{sessionStorage.removeItem("token"); setSignedIn(false); setProfileActive(false); window.location.reload()}}>Sign out</div> {/* TODO: check if you should reload page*/}
            </div> {/* Profile Box Handling, sign up redirection */}
        </div>
}

export default Header