import api from './axios'

const BOOK_SUBSCRIPTION_URL = '/bookSubscriptions'
const MONTHLY_SUBSCRIPTION_URL = '/subscribes'

export const subscriptionApi = {
  // 도서 구독 관련
  bookSubscription: {
    // 도서 구독
    subscribeBook: data => api.post(`${BOOK_SUBSCRIPTION_URL}/subscribebook`, data),

    // 도서 구독시 도서 조회
    viewBook: data => api.post(`${BOOK_SUBSCRIPTION_URL}/viewbook`, data),
  },

  // 월간 구독 관련
  monthlySubscription: {
    // 월간 구독 요청
    subscribeRequest: data => api.post(`${MONTHLY_SUBSCRIPTION_URL}/subscriberequest`, data),

    // 월간 구독 취소 요청
    unsubscribeRequest: (id, data, headers = {}) =>
      api.delete(`${MONTHLY_SUBSCRIPTION_URL}/${id}/unsubscriberequest`, {
        data,
        headers: {
          'X-User-Id': headers.userId || '',
          'X-User-Role': headers.userRole || '',
          ...headers,
        },
      }),

    // 구독 조회
    getSubscription: () => api.get(`${MONTHLY_SUBSCRIPTION_URL}Views`),
  },
}
