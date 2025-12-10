import ReadingPage from './ReadingPage'
import './App.css'
import {Route, Routes} from 'react-router-dom'

function App() {
  return <>
    <Routes>  
      <Route path='/chapter/:chapterId' element={<ReadingPage/>}></Route>
    </Routes>
  </>
}

export default App
