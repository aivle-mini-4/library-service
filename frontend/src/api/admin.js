import api from './axios'

const AUTHOR_APPROVAL_URL = '/authorapprovals'
const POINT_POLICY_URL = '/pointpolicies'

export const adminApi = {
  // 작가 승인 관련
  authorApproval: {
    // 작가 승인 요청 생성
    create: data => api.post(AUTHOR_APPROVAL_URL, data),

    // 전체 승인 요청 목록 조회
    getAll: () => api.get(AUTHOR_APPROVAL_URL),

    // 단일 승인 요청 조회
    getById: id => api.get(`${AUTHOR_APPROVAL_URL}/${id}`),

    // 승인 처리
    approve: (id, adminId) =>
      api.put(`${AUTHOR_APPROVAL_URL}/${id}/approve`, null, {
        params: { adminId },
      }),

    // 거부 처리
    reject: (id, adminId, reason) =>
      api.put(`${AUTHOR_APPROVAL_URL}/${id}/reject`, null, {
        params: { adminId, reason },
      }),
  },

  // 포인트 정책 관련
  pointPolicy: {
    // 포인트 정책 생성
    create: data => api.post(POINT_POLICY_URL, data),

    // 전체 포인트 정책 목록 조회
    getAll: () => api.get(POINT_POLICY_URL),

    // 특정 포인트 정책 단건 조회
    getById: id => api.get(`${POINT_POLICY_URL}/${id}`),

    // 포인트 정책 수정
    update: (id, data) => api.put(`${POINT_POLICY_URL}/${id}`, data),

    // 포인트 정책 삭제
    delete: id => api.delete(`${POINT_POLICY_URL}/${id}`),
  },
}
