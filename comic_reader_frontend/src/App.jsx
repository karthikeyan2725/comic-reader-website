import {Route, Routes} from 'react-router-dom'

import ReadingPage from './ReadingPage'
import Header from './Header'
import Footer from './Footer'
import './App.css'
import './Color.css'

function App() {
  return <>
    <Header/>
      <Routes>  
        <Route path='/chapter/:chapterId' element={<ReadingPage/>}></Route>
      </Routes>
    <Footer/>
  </>
}

export default App
