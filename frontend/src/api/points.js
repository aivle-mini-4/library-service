import api from './axios'

const POINTS_URL = '/points'
const POINT_VIEWS_URL = '/pointViews'

export const pointsApi = {
  // 포인트 관리 (Point Management Service - Spring Data REST)
  points: {
    // 포인트 전체 목록 조회
    getPoints: (params = {}) => api.get(POINTS_URL, { params }),

    // 포인트 단일 조회
    getPoint: id => api.get(`${POINTS_URL}/${id}`),

    // 포인트 생성
    createPoint: data => api.post(POINTS_URL, data),

    // 포인트 수정
    updatePoint: (id, data) => api.put(`${POINTS_URL}/${id}`, data),

    // 포인트 삭제
    deletePoint: id => api.delete(`${POINTS_URL}/${id}`),
  },

  // 포인트 뷰 (Point Management Service - Spring Data REST)
  pointViews: {
    // 포인트 뷰 전체 목록 조회
    getPointViews: (params = {}) => api.get(POINT_VIEWS_URL, { params }),

    // 포인트 뷰 단일 조회
    getPointView: id => api.get(`${POINT_VIEWS_URL}/${id}`),

    // 사용자별 포인트 뷰 조회
    getPointViewsByUserId: userId =>
      api.get(`${POINT_VIEWS_URL}/search/findByUserId`, {
        params: { userId },
      }),
  },
}
