import api from './axios'

const ADMIN_PAGES_URL = '/adminPages'
const USER_PAGES_URL = '/userPages'
const ADMIN_PROFILES_URL = '/adminProfiles'
const AUTHOR_PROFILE_URL = '/authorProfile'
const MEMBER_PROFILES_URL = '/memberProfiles'

export const userInfoApi = {
  // 페이지 관련
  pages: {
    // 관리자 페이지
    adminPages: {
      getAll: (params = {}) => api.get(ADMIN_PAGES_URL, { params }),
      getById: id => api.get(`${ADMIN_PAGES_URL}/${id}`),
      create: data => api.post(ADMIN_PAGES_URL, data),
      update: (id, data) => api.put(`${ADMIN_PAGES_URL}/${id}`, data),
      delete: id => api.delete(`${ADMIN_PAGES_URL}/${id}`),
    },

    // 사용자 페이지
    userPages: {
      getAll: (params = {}) => api.get(USER_PAGES_URL, { params }),
      getById: id => api.get(`${USER_PAGES_URL}/${id}`),
      create: data => api.post(USER_PAGES_URL, data),
      update: (id, data) => api.put(`${USER_PAGES_URL}/${id}`, data),
      delete: id => api.delete(`${USER_PAGES_URL}/${id}`),
    },
  },

  // 프로필 관련 (User Info Management Service)
  profiles: {
    // 관리자 프로필
    adminProfiles: {
      getAll: (params = {}) => api.get(ADMIN_PROFILES_URL, { params }),
      getById: id => api.get(`${ADMIN_PROFILES_URL}/${id}`),
      create: data => api.post(ADMIN_PROFILES_URL, data),
      update: (id, data) => api.put(`${ADMIN_PROFILES_URL}/${id}`, data),
      delete: id => api.delete(`${ADMIN_PROFILES_URL}/${id}`),
    },

    // 작가 프로필 수정
    authorProfile: {
      get: () => api.get(`${AUTHOR_PROFILE_URL}`),
      update: (data) => api.put(`${AUTHOR_PROFILE_URL}`, data),
    },

    // 회원 프로필 수정
    memberProfiles: {
      update: (id, data) => api.put(`${MEMBER_PROFILES_URL}/${id}`, data),
    },
  },
}
