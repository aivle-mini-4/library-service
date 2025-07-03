import api from './axios'

const BOOKS_URL = '/books'
const BOOK_VIEWS_URL = '/book-views'
const BESTSELLER_VIEWS_URL = '/bestseller-views'

export const booksApi = {
  // 도서 관리 (Books Management Service)
  books: {
    // 도서 삭제
    deleteBook: id => api.delete(`${BOOKS_URL}/${id}`),
  },

  // 도서 조회 (Books Management Service)
  bookViews: {
    // 전체 도서 목록 조회
    getAllBooks: () => api.get(BOOK_VIEWS_URL),

    // 특정 도서 조회
    getBook: bookId => api.get(`${BOOK_VIEWS_URL}/${bookId}`),
  },

  // 베스트셀러 조회 (Books Management Service)
  bestSellerViews: {
    // 전체 베스트셀러 목록 조회
    getAllBestSellers: () => api.get(BESTSELLER_VIEWS_URL),

    // 특정 베스트셀러 조회
    getBestSeller: bookId => api.get(`${BESTSELLER_VIEWS_URL}/${bookId}`),
  },

  // 도서 목록 조회 (페이징 지원)
  getBooks: (params = {}) => api.get(BOOKS_URL, { params }),

  // 단일 도서 조회
  getBook: id => api.get(`${BOOKS_URL}/${id}`),

  // 도서 생성
  createBook: data => api.post(BOOKS_URL, data),

  // 도서 수정
  updateBook: (id, data) => api.put(`${BOOKS_URL}/${id}`, data),

  createCover: data => api.post(`${BOOKS_URL}/cover`, data),
  searchBooks: keyword => api.get(`${BOOKS_URL}/keyword`, { params: { keyword } }),
}
