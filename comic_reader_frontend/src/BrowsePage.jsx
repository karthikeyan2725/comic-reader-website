import { Eye, MessageSquare, Bookmark, StarIcon, Book } from 'lucide-react'

import './BrowsePage.css'

function BrowsePage(){
    return <div className='browse-page'>
            <div className='filter-panel'>

                <h4 className='filter-title'>Genres</h4> {/* TODO: Move to component */}
                <ul className='filter-genres'>
                    {[1,2,3,4,5,6,7,8].map(()=>
                        <li className='filter-list-item'>
                            <div>Action</div> 
                            <div className='filter-checkbox' type='checkbox'></div>
                        </li>
                    )}
                </ul>

                <h4 className='filter-title'>Sort By</h4>
                <ul className='sort-by'>
                    {[1,2,3,4,5,6].map(()=>
                        <li className='filter-list-item'>
                            <div>Chapter Name</div> 
                            <div className='filter-checkbox' type='checkbox'></div>
                        </li>
                    )}
                </ul>

                <h4 className='filter-title'>Publication Span</h4>
                <div>  {/*TODO: Make custom 2 handle slider*/}
                    After
                    <input type="range"></input>
                    Before
                    <input type="range"></input>
                </div>

                <h4 className='filter-title'>Language</h4>
                <ul className='languages'>
                    {[1,2].map(()=>
                        <li className='filter-list-item'>
                            <div>Japanese</div> 
                            <div className='filter-checkbox' type='checkbox'></div>
                        </li>
                    )}
                </ul>
            </div>

            <div className="search-panel">
                <div className='advanced-search-bar'> {/* TODO : Make this the bar...*/ }
                    <input className="search-bar" type="text" placeholder='search...'/>
                </div>

                <div className="search-result-container">

                    {[1,2,3,4,5,6,7,8,9,10].map(()=>
                        <div className='search-item'> {/* TODO: Make this search item (or) remove if possible */}
                            <div className="search-item-body">
                                <div className='info-row'>
                                    <img className='cover-art' src='https://jumpg-assets.tokyo-cdn.com/secure/title/100171/title_thumbnail_portrait_list/312235.jpg?hash=ov0SDV1k1eVUfWumGcn1zw&expires=2145884400'></img>
                                    <div className='info-text'>
                                        <h2 className='comic-name'>Dandadan</h2>
                                        <p className='description'>After being aggressively rejected, Momo Ayase finds herself sulking when she stumbles across a boy being bullied. Saved by her rash kindness, the occult-obsessed boy attempts to speak to her about supernatural interests he believes they share. Rejecting his claims, Ayase proclaimed that she is instead a believer in ghosts, starting an argument between the two over which is real. In a bet to determine who's correct, the two decide to separately visit locations associated with both the occult and the supernatural—Ayase visiting the former and the boy visiting the latter. When the two reach their respective places, it turns out that neither of them was wrong and that both the occult and ghosts do exist.</p>
                                    </div>
                                </div>
                                <div className='genre-row'>
                                    <ul className='genre-list'>
                                        <li className='genre-item'>Action</li>
                                        <li className='genre-item'>Action</li>
                                        <li className='genre-item'>Action</li>
                                        <li className='genre-item'>Action</li>
                                    </ul>
                                    <div className='more-genre'>more...</div> {/* TODO: Add onClick at the end */}
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
                    )}
                </div>
            </div>
        </div>
}

export default BrowsePage