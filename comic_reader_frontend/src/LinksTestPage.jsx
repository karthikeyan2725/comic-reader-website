import { Link } from "react-router-dom"

import "./LinksTestPage.css"

function LinksTestPage(){
    return <ul className="test1">
        <li><Link className = "test2" to="/comic/1957">Naruto</Link></li>
        <li><Link className = "test2" to="/comic/1954">AOT</Link></li>
        <li><Link className = "test2" to="/comic/1956">Kaiju No 8</Link></li>
        <li><Link className = "test2" to="/comic/1955">Sakamoto Days</Link></li>
        <li><Link className = "test2" to="/comic/1952">Bakuman</Link></li>
        <li><Link className = "test2" to="/comic/1953">Solo Leveling</Link></li>
    </ul>
}

export default LinksTestPage