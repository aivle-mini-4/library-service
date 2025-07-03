import { useEffect, useState } from 'react'
import { booksApi } from '../api/books'

export const useBookDetail = bookId => {
  const [book, setBook] = useState(null)
  const [isSubscriber, setIsSubscriber] = useState(false)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [actionLoading, setActionLoading] = useState(false)
  const [actionMessage, setActionMessage] = useState(null)

  const fetchBookDetail = async () => {
    try {
      setLoading(true)
      setError(null)
      const response = await booksApi.getBookDetail(bookId)
      setBook(response.data)

      // 구독자 여부 확인 (임시로 랜덤하게 설정)
      setIsSubscriber(Math.random() > 0.5)
    } catch (err) {
      setError('책 정보를 불러오는데 실패했습니다.')
      console.error('책 상세 조회 실패:', err)
    } finally {
      setLoading(false)
    }
  }

  const handleViewBook = async () => {
    try {
      setActionLoading(true)
      await booksApi.viewBook(bookId)
      setActionMessage({ type: 'success', text: '책을 성공적으로 열람했습니다!' })

      // 실제로는 책 내용 페이지로 이동하거나 모달을 띄우는 등의 처리
      setTimeout(() => {
        setActionMessage(null)
      }, 3000)
    } catch (err) {
      setActionMessage({ type: 'error', text: '책 열람에 실패했습니다.' })
      console.error('책 열람 실패:', err)
    } finally {
      setActionLoading(false)
    }
  }

  const handleSubscribe = async () => {
    try {
      setActionLoading(true)
      await booksApi.subscribeBook(bookId)
      setActionMessage({ type: 'success', text: '알람 신청이 완료되었습니다!' })
      setIsSubscriber(true)

      setTimeout(() => {
        setActionMessage(null)
      }, 3000)
    } catch (err) {
      setActionMessage({ type: 'error', text: '알람 신청에 실패했습니다.' })
      console.error('알람 신청 실패:', err)
    } finally {
      setActionLoading(false)
    }
  }

  const clearActionMessage = () => {
    setActionMessage(null)
  }

  useEffect(() => {
    if (bookId) {
      fetchBookDetail()
    }
  }, [bookId])

  return {
    // 상태
    book,
    isSubscriber,
    loading,
    error,
    actionLoading,
    actionMessage,

    // 액션
    handleViewBook,
    handleSubscribe,
    clearActionMessage,
    refetch: fetchBookDetail,
  }
}
