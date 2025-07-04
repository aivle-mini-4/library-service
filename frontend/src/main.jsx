import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App'
import './main.css'
import { worker } from './mocks/browser'

async function prepare() {
  if (import.meta.env.DEV) {
    return worker.start({
      onUnhandledRequest: 'bypass',
    })
  }
  return Promise.resolve()
}

prepare().then(() => {
  ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
      <App />
    </React.StrictMode>,
  )
})
