import {useState} from 'react'
import {useDispatch} from 'react-redux'
import {useNavigate} from 'react-router-dom'
import {authApi} from '../api/auth'
import {login} from '../store/authSlice'

function Login() {
  const [loginId, setLoginId] = useState('')
  const [password, setPassword] = useState('')
  const navigate = useNavigate()
  const dispatch = useDispatch()

  const handleSubmit = async (e) => {
    e.preventDefault()
    try {
      const response = await authApi.login({loginId, password})
      if (response?.data?.isSuccess) {
        dispatch(login({
          user: response.data.result.memberId,
          token: 'access-token'
        }))
        navigate('/')
      }
    } catch (error) {
      console.error('로그인 실패:', error)
    }
  }

  return (
    <div>
      <h1>Login</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <input
            type="text"
            value={loginId}
            onChange={(e) => setLoginId(e.target.value)}
            placeholder="아이디"
          />
        </div>
        <div>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="비밀번호"
          />
        </div>
        <button type="submit">로그인</button>
      </form>
    </div>
  )
}

export default Login 