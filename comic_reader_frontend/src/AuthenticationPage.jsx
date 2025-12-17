import { Link, useNavigate } from "react-router-dom"
import axios from "axios"
import { useEffect, useState } from "react"

import "./AuthenticationPage.css"

function AuthenticationPage({type}){

    const baseUrl = import.meta.env.VITE_comic_api_url

    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")

    const navigate = useNavigate()

    async function onSubmitHandler(event){

        event.preventDefault()

        var sucess = false;
        
        if(type == "sign-up"){ 
            try{
                const response = await axios.post(baseUrl + "/user/sign-up", {"email" : email, "password" : password}) // TODO: Encrypt Password
                if(response.status == 200) sessionStorage.setItem("token", response.data) 
                success = true
            } catch (err){
                console.error("Sign Up Failed:" + err.status)
            }
        }

        if(type == "sign-in"){
            try{
                const response = await axios.post(baseUrl + "/user/sign-in", {"email" : email, "password" : password})
                if(response.status == 200) sessionStorage.setItem("token", response.data) 
                    sucess = true
            } catch (err){
                console.error("Sign In Failed:" + err.status)
            }
        }

        if(sucess == true) navigate("/home") // TODO: navigate back to page from where sign in clicked
    }

    return <div className="auth-page">
                <div className="credential-panel">
                    <h1 className="welcome-text">WELCOME {(type == "sign-in") ? "BACK " : "NEW "}READER</h1>
                    <form onSubmit={onSubmitHandler}>
                        <div className="form-item">
                            <label>Email</label>
                            <input className="input-field" placeholder = "Enter your email address..." type="text" onChange={(event)=>{setEmail(event.target.value)}}/> 
                        </div>
                        <div className="form-item">
                            <label>Password</label>
                            <input className="input-field" placeholder = "Enter your password..." type="password" onChange={(event)=>{setPassword(event.target.value)}}/>
                        </div>
                        <input className="sign-in-button" type="submit" value={(type == "sign-in") ? "Sign In" : "Sign Up"}/>
                    </form>
                    {(type=="sign-in") ?
                        <Link className="click-here-link" to="/sign-up">or click here to sign up</Link>
                        :
                        <Link className="click-here-link" to="/sign-in">or click here to sign in</Link>
                    }
                </div>
        </div>
}

export default AuthenticationPage