import {useDispatch} from 'react-redux'
import {useNavigate} from 'react-router-dom'
import {logout} from '../store/authSlice'

function Home() {
  const dispatch = useDispatch()
  const navigate = useNavigate()
  return (
    <div>
      <h1>Home</h1>
      {/*logout*/}
      <button onClick={() => {

        dispatch(logout())
        navigate('/login')

      }}>로그아웃
      </button>
    </div>
  )
}

export default Home 