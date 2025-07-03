import axios from 'axios'

const api = axios.create({
  baseURL: '/api',
  headers: {
    'Content-Type': 'application/json',
  },
})

api.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers['Authorization'] = `Bearer ${token}`
  }
  return config
})

api.interceptors.response.use(
  response => {
    return response
  },
  error => {
    // 에러 일괄 처리가능
    // unauthorized 에러 발생 시 token 삭제
    if (error.response && error.response.status === 401) {
      console.error('Unauthorized request 로그아웃')
      store.dispatch(logout())
    }
    return Promise.reject(error)
  },
)
export default api
