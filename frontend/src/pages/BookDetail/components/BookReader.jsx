import { useState } from 'react'
import { booksApi } from '../../../api/books'
import { subscriptionApi } from '../../../api/subscription'
import Alert from '../../../components/Alert'
import Button from '../../../components/Button'
import Card from '../../../components/Card'
import Divider from '../../../components/Divider'
import { useApi } from '../../../hooks/useApi'
import BookInfo from './BookInfo'
import ErrorState from './ErrorState'

function BookReader({ bookId }) {
  const [isViewing, setIsViewing] = useState(false)
  const [actionMessage, setActionMessage] = useState(null)

  const { isLoading: isViewBookLoading, execute: executeViewBook } = useApi(subscriptionApi.bookSubscription.viewBook)

  const {
    isLoading: isBookLoading,
    error: bookError,
    data: bookData,
    isSuccess: isBookSuccess,
    execute: executeBookQuery,
  } = useApi(booksApi.getBook, { params: [bookId], runOnMount: true })

  // 월구독자 도서 열람
  const handleViewBook = async () => {
    try {
      setActionMessage({ type: 'info', text: '도서를 열람하는 중입니다...' })

      // 1. 월구독자 도서 열람
      await executeViewBook([{ userId: 1, isBookSubscribed: true }])

      // 2. 도서 조회 (열람 후)
      const bookResult = await executeBookQuery()

      const { isSuccess, result } = bookResult
      if (isSuccess && result) {
        setIsViewing(true)
        setActionMessage({ type: 'success', text: '도서 열람이 완료되었습니다.' })
      } else {
        throw new Error('도서 조회에 실패했습니다.')
      }
    } catch (error) {
      setActionMessage({ type: 'error', text: '도서 열람에 실패했습니다.' })
      console.error('도서 열람 실패:', error)
    }
  }

  // 다시 열람하기
  const handleRetry = () => {
    setActionMessage(null)
    setIsViewing(false)
  }

  // 로딩 상태
  if (isBookLoading) {
    return (
      <div className='min-h-screen flex items-center justify-center bg-blue-50'>
        <div className='text-center'>
          <div className='animate-spin rounded-full h-12 w-12 border-b-2 border-slate-600 mx-auto mb-4'></div>
          <p className='text-gray-600'>도서 정보를 불러오는 중...</p>
        </div>
      </div>
    )
  }

  // 에러 상태
  if (bookError) {
    return <ErrorState error={bookError || '도서 정보를 불러올 수 없습니다.'} />
  }

  // 도서가 없는 경우
  if (!bookData) {
    return <ErrorState error='도서를 찾을 수 없습니다.' />
  }

  return (
    <div className='grid grid-cols-1 lg:grid-cols-3 gap-8'>
      {/* 책 정보 카드 */}
      <div className='lg:col-span-2'>
        <BookInfo book={bookData} />
      </div>

      {/* 도서 열람 카드 */}
      <div className='lg:col-span-1'>
        {!isViewing ? (
          // 도서 열람 전 화면
          <Card className='h-fit'>
            {/* 액션 메시지 */}
            {actionMessage && (
              <div className='mb-6'>
                <Alert type={actionMessage.type}>{actionMessage.text}</Alert>
              </div>
            )}

            <div className='mb-6'>
              <h2 className='text-xl font-bold text-gray-900 mb-2'>도서 열람</h2>
              <p className='text-gray-600'>월 구독자이므로 이 책을 자유롭게 열람할 수 있습니다.</p>
            </div>

            <Divider className='my-6' />

            <div className='text-center'>
              <Button
                onClick={handleViewBook}
                loading={isViewBookLoading}
                fullWidth
                variant='primary'
              >
                책 열람하기
              </Button>
            </div>
          </Card>
        ) : (
          // 도서 열람 후 화면
          <Card className='h-fit'>
            <div className='mb-6'>
              <h2 className='text-xl font-bold text-gray-900 mb-2'>도서 내용</h2>
              <p className='text-gray-600'>이제 도서의 전체 내용을 열람할 수 있습니다.</p>
            </div>

            <Divider className='my-6' />

            <div className='prose max-w-none'>
              {bookData.content ? (
                <div className='whitespace-pre-wrap text-gray-700 leading-relaxed'>{bookData.content}</div>
              ) : (
                <div className='text-center py-12'>
                  <div className='text-gray-400 mb-4'>
                    <svg
                      className='w-16 h-16 mx-auto'
                      fill='currentColor'
                      viewBox='0 0 20 20'
                    >
                      <path
                        fillRule='evenodd'
                        d='M4 3a2 2 0 00-2 2v10a2 2 0 002 2h12a2 2 0 002-2V5a2 2 0 00-2-2H4zm12 12H4l4-8 3 6 2-4 3 6z'
                        clipRule='evenodd'
                      />
                    </svg>
                  </div>
                  <p className='text-gray-500'>도서 내용이 준비 중입니다.</p>
                </div>
              )}
            </div>

            <Divider className='my-6' />

            <div className='text-center'>
              <Button
                onClick={handleRetry}
                fullWidth
                variant='secondary'
              >
                다시 열람하기
              </Button>
            </div>

            <div className='text-sm text-gray-500 text-center mt-4'>
              <p>도서 열람이 완료되었습니다.</p>
              <p className='mt-1'>이 도서는 독서 기록에 저장됩니다.</p>
            </div>
          </Card>
        )}
      </div>
    </div>
  )
}

export default BookReader
