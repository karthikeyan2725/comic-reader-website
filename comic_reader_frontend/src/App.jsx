import {Route, Routes} from 'react-router-dom'

import ReadingPage from './ReadingPage'
import ChapterListingPage from './ChapterListingPage'
import Header from './common/Header'
import Footer from './common/Footer'
import './App.css'
import './Color.css'

function App() {
  return <>
    <Header/>
      <Routes>  
        <Route path='/chapter/:chapterId' element={<ReadingPage/>}></Route>
        <Route path='/comic/:comicId' element={<ChapterListingPage/>}></Route>
      </Routes>
    <Footer/>
  </>
}

export default App
