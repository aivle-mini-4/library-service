import api from './axios'

const MEMBER_PROFILES_URL = '/memberProfiles'
const VIEW_HISTORIES_URL = '/viewHistories'
const FAVORITES_URL = '/favorites'
const SUBSCRIBE_VIEWS_URL = '/subscribeViews'
const SUBSCRIBES_URL = '/subscribes'
const POINTS_URL = '/points'
const POINT_VIEWS_URL = '/pointViews'

export const mypageApi = {
  // 내 정보 수정 - 사용자 ID 필요
  memberProfiles: {
    getById: userId => api.get(`${MEMBER_PROFILES_URL}/${userId}`),
    update: (userId, data) => api.put(`${MEMBER_PROFILES_URL}/${userId}`, data),
  },

  // 열람 이력
  viewHistories: {
    getAll: (params = {}) => api.get(VIEW_HISTORIES_URL, { params }),
  },

  // 즐겨찾기
  favorites: {
    getAll: (params = {}) => api.get(FAVORITES_URL, { params }),
  },

  // 구독 관리
  subscription: {
    getSubscribeViews: () => api.get(SUBSCRIBE_VIEWS_URL),
    createSubscribe: data => api.post(SUBSCRIBES_URL, data),
    deleteSubscribe: id => api.delete(`${SUBSCRIBES_URL}/${id}`),
  },

  // 포인트 관리
  points: {
    getAll: (params = {}) => api.get(POINTS_URL, { params }),
    getPointViews: (params = {}) => api.get(POINT_VIEWS_URL, { params }),
  },
}
