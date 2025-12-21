import { Link, useNavigate } from "react-router-dom"
import { useRef, useState } from "react"
import { ToastContainer, toast } from "react-toastify"
import ReCAPTCHA from "react-google-recaptcha"
import axios from "axios"

import "./AuthenticationPage.css"

function AuthenticationPage({type}){ 

    const baseUrl = import.meta.env.VITE_comic_api_url
    const reEmail = new RegExp(".+@gmail\.com")

    const navigate = useNavigate()
    const captcha = useRef()
    const [captchaAuthenticated, setCaptchaAuthenticated] = useState(false)
    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")

    async function onSubmitHandler(event){ // TODO: Think if you should move error to panel or toast, style toast, Handle Refill captcha

        event.preventDefault()

        if(!reEmail.test(email)) return toast.warn("Please Fill in a valid Email Address")
        if(password.length < 8) return toast.warn("Password should be atleast 8 characters")
        if(!captchaAuthenticated) return toast.warn("Please Fill the captcha, Dear reader")

        var success = false;
        
        if(type == "sign-up"){ 
            try{
                const response = await axios.post(baseUrl + "/user/sign-up", {"email" : email, "password" : password}) // TODO: Encrypt Password
                if(response.status == 200) sessionStorage.setItem("token", response.data) 
                success = true
            } catch (err){
                toast.error("Please try a another email to sign up")
                console.error("Sign Up Failed:" + err.status)
            }
        }

        if(type == "sign-in"){
            try{
                const response = await axios.post(baseUrl + "/user/sign-in", {"email" : email, "password" : password})
                if(response.status == 200) sessionStorage.setItem("token", response.data) 
                success = true
            } catch (err){
                toast.error("Please provide correct credentials")
                console.error("Sign In Failed:" + err.status)
            }
        }

        if(success == true) {
            navigate("/") // TODO: navigate back to page from where sign in clicked
            window.location.reload()
        }
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
                        <ReCAPTCHA sitekey="6LfuhzIsAAAAANYKSgCWFoz-MKeLMALk9qBic72v" onChange={(value)=>{value; setCaptchaAuthenticated(true)}} ref={captcha}/> {/* TODO: Implement Backend captcha check with value from onChange*/}
                        <input className="sign-in-button" type="submit" value={(type == "sign-in") ? "Sign In" : "Sign Up"}/>
                    </form>

                    {(type=="sign-in") ?
                        <Link className="click-here-link" to="/sign-up">or click here to sign up</Link>
                        :
                        <Link className="click-here-link" to="/sign-in">or click here to sign in</Link>
                    }
                </div>
                
                <ToastContainer position="bottom-left" theme="dark"/>
        </div>
}

export default AuthenticationPage