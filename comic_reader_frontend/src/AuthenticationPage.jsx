import { Link } from "react-router-dom"
import axios from "axios"
import { useEffect, useState } from "react"

import "./AuthenticationPage.css"

function AuthenticationPage({type}){ // TODO: Redesign UI

    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")

    async function onSubmitHandler(event){
        event.preventDefault()

        console.log("Performing: " + type)
        const response = await axios.get("http://localhost:8080/chapter/2202")
    }

    useEffect(()=>{
        console.log("Email : " + email + " Password : " + password)
    }, [email, password])

    return <div className="auth-page">
        <div className="credential-panel">
            <h1 className="welcome-text">WELCOME {(type == "sign-in") ? "BACK " : "NEW "}READER</h1>
            <form onSubmit={onSubmitHandler}>
                <div className="form-item">
                    <label>Email</label>
                    <input className="input-field" placeholder = "Enter your email address..." type="text" onChange={(event)=>{setEmail(event.target.value)}}/> {/* TODO: Decide to move lambda outside html*/}
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