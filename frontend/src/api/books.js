import api from './axios'

const BASE_URL = '/books'

export const booksApi = {
  getBooks: (title) => api.get(BASE_URL, { params: { title } }),
  getBook: (bookId) => api.get(`${BASE_URL}/${bookId}`),
  createBook: (data) => api.post(BASE_URL, data),
  updateBook: (bookId, data) => api.put(`${BASE_URL}/${bookId}`, data),
  deleteBook: (bookId) => api.delete(`${BASE_URL}/${bookId}`),
  createCover: (data) => api.post(`${BASE_URL}/cover`, data),
  searchBooks: (keyword) => api.get(`${BASE_URL}/keyword`, { params: { keyword } })
} 