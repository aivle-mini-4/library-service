import api from './axios'

const BASE_URL = '/members'

export const authApi = {
  register: (data) => api.post(`${BASE_URL}/new`, data),
  login: (data) => api.post(`${BASE_URL}/login`, data),
  getMe: () => api.get(`${BASE_URL}/me`)
} 