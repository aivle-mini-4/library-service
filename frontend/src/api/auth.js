import api from './axios'

const BASE_URL = '/auth'

export const authApi = {
  // 관리자 계정 회원가입
  adminSignup: data => api.post('/adminAccounts/signup', data),

  // 작가 계정 회원가입
  authorSignup: data => api.post('/authorAccounts/signup', data),

  // 사용자 계정 회원가입
  userSignup: data => api.post('/userAccounts/signup', data),

  // 로그인
  login: data => api.post(`${BASE_URL}/login`, data),

  // 토큰 유효성 검사
  validateToken: () => api.post(`${BASE_URL}/validate`),
}
