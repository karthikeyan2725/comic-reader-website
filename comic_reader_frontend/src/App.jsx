import {Route, Routes} from 'react-router-dom'
import { useLocation } from 'react-router-dom'

import ReadingPage from './ReadingPage'
import ChapterListingPage from './ChapterListingPage'
import Header from './common/Header'
import Footer from './common/Footer'
import LinksTestPage from './LinksTestPage'
import AuthenticationPage from './AuthenticationPage'
import HomePage from './HomePage'

import './App.css'
import './Variables.css'

function App() {
  let {pathname} = useLocation()

  const disableFooterLocations = ["/sign-in", "/sign-up"]

  return <>
    <Header/>
      <Routes>  
        <Route path='/home' element={<HomePage/>}></Route>
        <Route path='/chapter/:chapterId' element={<ReadingPage/>}></Route> 
        <Route path='/comic/:comicId' element={<ChapterListingPage/>}></Route>
        <Route path='/links' element={<LinksTestPage/>}></Route>
        <Route path='/sign-in' element={<AuthenticationPage type="sign-in"/>}></Route> {/* TODO :Use enum if possible for type*/}
        <Route path='/sign-up' element={<AuthenticationPage type="sign-up"/>}></Route> 
      </Routes>
    {(disableFooterLocations.includes(pathname)) ?  null : <Footer/>}
  </>
}

export default App