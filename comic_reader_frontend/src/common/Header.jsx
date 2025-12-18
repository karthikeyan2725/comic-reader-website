import { Link } from "react-router-dom";

import "./Header.css";

function Header(){
    return <div className = 'header'>
            <div className = 'logo'>ComicRaven</div>
            <nav className = 'links'> 
                <ul>
                    <li><Link className = "link" to="/">Home</Link></li> 
                    <li><Link className = "link">Browse</Link></li>
                </ul>
            </nav>
            <input className = 'search' type='text' placeholder='Search'></input>
            <Link className = 'profile' to="/sign-in">P</Link> {/* Profile Box Handling, sign up redirection */}
        </div>
}

export default Header;