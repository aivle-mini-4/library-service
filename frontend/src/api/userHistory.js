import api from './axios'

const FAVORITES_URL = '/favorites'
const VIEW_HISTORIES_URL = '/viewHistories'
const QUERY_FAVORITE_LISTS_URL = '/queryFavoriteLists'
const QUERY_VIEW_HISTORIES_URL = '/queryViewHistories'

export const userHistoryApi = {
  // 즐겨찾기 관련 (User History Management Service - Spring Data REST)
  favorites: {
    // 즐겨찾기 목록 조회
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

  // 조회 히스토리 관련 (User History Management Service - Spring Data REST)
  viewHistories: {
    // 조회 히스토리 목록 조회
    getViewHistories: (params = {}) => api.get(VIEW_HISTORIES_URL, { params }),

    // 조회 히스토리 단건 조회
    getViewHistory: id => api.get(`${VIEW_HISTORIES_URL}/${id}`),

    // 조회 히스토리 등록
    createViewHistory: data => api.post(VIEW_HISTORIES_URL, data),

    // 조회 히스토리 수정
    updateViewHistory: (id, data) => api.put(`${VIEW_HISTORIES_URL}/${id}`, data),

    // 조회 히스토리 삭제
    deleteViewHistory: id => api.delete(`${VIEW_HISTORIES_URL}/${id}`),
  },

  // 쿼리 즐겨찾기 관련 (User History Management Service - Spring Data REST)
  queryFavoriteLists: {
    // 쿼리 즐겨찾기 목록 조회
    getQueryFavoriteLists: (params = {}) => api.get(QUERY_FAVORITE_LISTS_URL, { params }),

    // 쿼리 즐겨찾기 단건 조회
    getQueryFavoriteList: id => api.get(`${QUERY_FAVORITE_LISTS_URL}/${id}`),
  },

  // 쿼리 조회 히스토리 관련 (User History Management Service - Spring Data REST)
  queryViewHistories: {
    // 쿼리 조회 히스토리 목록 조회
    getQueryViewHistories: (params = {}) => api.get(QUERY_VIEW_HISTORIES_URL, { params }),

    // 쿼리 조회 히스토리 단건 조회
    getQueryViewHistory: id => api.get(`${QUERY_VIEW_HISTORIES_URL}/${id}`),
  },
}
