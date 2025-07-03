import api from './axios'

const BASE_URL = '/manuscripts'

export const manuscriptsApi = {
  // 전체 원고 페이지 목록 조회
  getManuscripts: () => api.get(BASE_URL),

  // 특정 원고 페이지 단건 조회
  getManuscript: id => api.get(`${BASE_URL}/${id}`),

  // 원고 생성
  createManuscript: data => api.post(BASE_URL, data),

  // 원고 수정
  updateManuscript: (id, data) => api.put(`${BASE_URL}/${id}`, data),

  // 원고 삭제
  deleteManuscript: id => api.delete(`${BASE_URL}/${id}`),

  // 원고 출판 요청 이벤트 발생
  requestPublication: id => api.post(`${BASE_URL}/${id}`),
}
