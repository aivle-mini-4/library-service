// AI Service는 Kafka 이벤트 기반으로 동작하며 REST API 엔드포인트를 제공하지 않습니다.
// PublicationRequested 이벤트를 수신하여 자동으로 도서 출판 처리를 수행합니다.

// const IMAGES_URL = '/images/generations'
// const CHAT_URL = '/chat/completions'

export const aiApi = {
  // AI 서비스는 Kafka 이벤트 기반으로 동작합니다.
  // REST API 엔드포인트가 없습니다.
  // 이미지 생성 요청 (현재 사용되지 않음)
  // generateImage: data => api.post(IMAGES_URL, data),
  // 챗 응답 생성 요청 (현재 사용되지 않음)
  // createChatCompletion: data => api.post(CHAT_URL, data),
}
