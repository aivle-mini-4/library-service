import api from './axios'

const BASE_URL = '/books'

export const booksApi = {
  // 도서 목록 조회 (페이징 지원)
  getBooks: (params = {}) => api.get(BASE_URL, { params }),

  // 단일 도서 조회
  getBook: id => api.get(`${BASE_URL}/${id}`),

  // 도서 생성
  createBook: data => api.post(BASE_URL, data),

  // 도서 수정
  updateBook: (id, data) => api.put(`${BASE_URL}/${id}`, data),

  // 도서 삭제
  deleteBook: id => api.delete(`${BASE_URL}/${id}`),

  createCover: data => api.post(`${BASE_URL}/cover`, data),
  searchBooks: keyword => api.get(`${BASE_URL}/keyword`, { params: { keyword } }),
}
