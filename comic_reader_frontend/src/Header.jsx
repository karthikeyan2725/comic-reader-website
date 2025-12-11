import { Link } from "react-router-dom";

import "./Header.css";

function Header(){
    return <div className = 'header'>
            <div className = 'logo'>ComicRaven</div>
            <nav className = 'links'> 
                <ul>
                    <li><Link className = "link">Home</Link></li> {/* TODO: check if <a> vs <Link> */}
                    <li><Link className = "link">Browse</Link></li>
                </ul>
            </nav>
            <input className = 'search' type='text' placeholder='Search'></input>
            <div className = 'profile'>P</div>
        </div>
}

export default Header;