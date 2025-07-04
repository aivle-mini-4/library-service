import api from './axios'

const BOOKS_URL = '/books'
const BESTSELLERS_URL = '/bestsellers'

export const booksApi = {
  // 도서 관리 (Books Management Service)
  books: {
    // 도서 삭제
    deleteBook: id => api.delete(`${BOOKS_URL}/${id}`),

    // 전체 도서 목록 조회
    getAllBooks: () => api.get(BOOKS_URL),

    // 특정 도서 조회
    getBook: bookId => api.get(`${BOOKS_URL}/${bookId}`),
  },

  // 베스트셀러 조회 (Books Management Service)
  bestSellers: {
    // 전체 베스트셀러 목록 조회
    getAllBestSellers: () => api.get(BESTSELLERS_URL),

    // 특정 베스트셀러 조회
    getBestSeller: bookId => api.get(`${BESTSELLERS_URL}/${bookId}`),
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

  // 도서 상세 조회
  getBookDetail: async id => {
    const response = await api.get(`/books/${id}`)
    return response
  },

  // 월 구독자 도서 열람
  viewBook: async bookId => {
    const response = await api.get('/bookSubscriptions/viewbook', {
      params: { bookId },
    })
    return response
  },

  // 비 구독자 도서 알람 신청
  subscribeBook: async bookId => {
    const response = await api.put('/bookSubscriptions/subscribebook', {
      userId: 'temp-user-123', // 임시 사용자 ID
      bookId: bookId,
    })
    return response
  },
}
