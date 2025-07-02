import {useSelector} from 'react-redux'
import {Navigate, Outlet, Route, Routes} from 'react-router-dom'
import BookDetail from '../pages/BookDetail'
import BookForm from '../pages/BookForm'
import Home from '../pages/Home'
import Login from '../pages/Login'
import Register from '../pages/Register'

const ProtectedRoute = () => {
  const {isAuthenticated} = useSelector((state) => state.auth)
  return isAuthenticated ? <Outlet/> : <Navigate to="/login" replace/>
}

const NotFoundRoute = () => {
  const {isAuthenticated} = useSelector((state) => state.auth)
  return isAuthenticated ? <Navigate to="/" replace/> : <Navigate to="/login" replace/>
}

function AppRoutes() {
  return (
    <Routes>
      <Route path="/login" element={<Login/>}/>
      <Route path="/register" element={<Register/>}/>
      <Route element={<ProtectedRoute/>}>
        <Route path="/" element={<Home/>}/>
        <Route path="/">
          <Route path=":id" element={<BookDetail/>}/>
          <Route path="write" element={<BookForm/>}/>
          <Route path=":id/edit" element={<BookForm/>}/>
        </Route>
      </Route>
      <Route path="*" element={<NotFoundRoute/>}/>
    </Routes>
  )
}

export default AppRoutes 