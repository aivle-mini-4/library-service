import api from './axios'

const IMAGES_URL = '/images/generations'
const CHAT_URL = '/chat/completions'

export const aiApi = {
  // 이미지 생성 요청
  generateImage: data => api.post(IMAGES_URL, data),

  // 챗 응답 생성 요청
  createChatCompletion: data => api.post(CHAT_URL, data),
}
