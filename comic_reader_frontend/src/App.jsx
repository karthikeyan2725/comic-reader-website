import {Route, Routes} from 'react-router-dom'

import ReadingPage from './ReadingPage'
import ChapterListingPage from './ChapterListingPage'
import Header from './common/Header'
import Footer from './common/Footer'
import LinksTestPage from './LinksTestPage'
import AuthenticationPage from './AuthenticationPage'

import './App.css'
import './Color.css'

function App() {
  return <>
    <Header/>
      <Routes>  
        <Route path='/chapter/:chapterId' element={<><ReadingPage/><Footer/></>}></Route> {/* TODO : Change this way of nesting footer */}
        <Route path='/comic/:comicId' element={<><ChapterListingPage/><Footer/></>}></Route>
        <Route path='/links' element={<><LinksTestPage/><Footer/></>}></Route>
        <Route path='/sign-in' element={<AuthenticationPage type="sign-in"/>}></Route> {/* Use enum if possible for type*/}
        <Route path='/sign-up' element={<AuthenticationPage type="sign-up"/>}></Route> 
      </Routes>
  </>
}

export default App