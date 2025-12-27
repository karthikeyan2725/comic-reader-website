import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter } from 'react-router-dom'
import App from './App.jsx'

createRoot(document.getElementById('root')).render(
  // <StrictMode> {/*TODO: Turn back on strict mode after handling toggle events */}
    <BrowserRouter>
      <App />
    </BrowserRouter>
  // </StrictMode>
)