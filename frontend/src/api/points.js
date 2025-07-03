import api from './axios'

const BASE_URL = '/points'

export const pointsApi = {
  // 포인트 전체 목록 조회 (페이징 지원)
  getPoints: (params = {}) => api.get(BASE_URL, { params }),

  // 포인트 단일 조회
  getPoint: id => api.get(`${BASE_URL}/${id}`),

  // 포인트 생성
  createPoint: data => api.post(BASE_URL, data),

  // 포인트 수정
  updatePoint: (id, data) => api.put(`${BASE_URL}/${id}`, data),

  // 포인트 삭제
  deletePoint: id => api.delete(`${BASE_URL}/${id}`),
}
