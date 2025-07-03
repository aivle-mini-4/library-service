import api from './axios'

const FAVORITES_URL = '/favorites'
const VIEW_HISTORIES_URL = '/viewHistories'

export const userHistoryApi = {
  // 즐겨찾기 관련
  favorites: {
    // 즐겨찾기 목록 조회 (페이징 지원)
    getFavorites: (params = {}) => api.get(FAVORITES_URL, { params }),

    // 즐겨찾기 단건 조회
    getFavorite: id => api.get(`${FAVORITES_URL}/${id}`),

    // 즐겨찾기 등록
    createFavorite: data => api.post(FAVORITES_URL, data),

    // 즐겨찾기 수정
    updateFavorite: (id, data) => api.put(`${FAVORITES_URL}/${id}`, data),

    // 즐겨찾기 삭제
    deleteFavorite: id => api.delete(`${FAVORITES_URL}/${id}`),
  },

  // 열람 이력 관련
  viewHistories: {
    // 열람 이력 목록 조회 (페이징 지원)
    getViewHistories: (params = {}) => api.get(VIEW_HISTORIES_URL, { params }),

    // 열람 이력 단건 조회
    getViewHistory: id => api.get(`${VIEW_HISTORIES_URL}/${id}`),

    // 열람 이력 등록
    createViewHistory: data => api.post(VIEW_HISTORIES_URL, data),

    // 열람 이력 수정
    updateViewHistory: (id, data) => api.put(`${VIEW_HISTORIES_URL}/${id}`, data),

    // 열람 이력 삭제
    deleteViewHistory: id => api.delete(`${VIEW_HISTORIES_URL}/${id}`),
  },
}
