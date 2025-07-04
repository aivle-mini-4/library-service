import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { subscriptionApi } from '../../../api/subscription'
import Alert from '../../../components/Alert'
import Button from '../../../components/Button'
import Card from '../../../components/Card'
import Divider from '../../../components/Divider'
import { useApi } from '../../../hooks/useApi'

function BookAccessRequest({ bookId, onSuccess, onError }) {
  const navigate = useNavigate()
  const [actionMessage, setActionMessage] = useState(null)

  const { isLoading: isSubscribeBookLoading, execute: executeSubscribeBook } = useApi(
    subscriptionApi.bookSubscription.subscribeBook,
  )

  // 비구독자 도서 열람 신청
  const handleSubscribeBook = async () => {
    try {
      setActionMessage({ type: 'info', text: '도서 열람을 신청하는 중입니다...' })

      // 비구독자 도서 열람 신청
      const result = await executeSubscribeBook([{ userId: 1, isBookSubscribed: false }])

      if (result.isSuccess) {
        setActionMessage({ type: 'success', text: '도서 열람 신청이 완료되었습니다.' })
        onSuccess?.(result)

        // 성공 시 BookReader로 라우팅 (isSubscriber: false)
        setTimeout(() => {
          navigate(`/${bookId}`, { state: { isSubscriber: false } })
        }, 1500)
      } else {
        throw new Error(result.error || '도서 열람 신청에 실패했습니다.')
      }
    } catch (error) {
      const errorMessage = '도서 열람 신청에 실패했습니다.'
      setActionMessage({ type: 'error', text: errorMessage })
      onError?.(error)
      console.error('도서 열람 신청 실패:', error)
    }
  }

  // 다시 신청하기
  const handleRetry = () => {
    setActionMessage(null)
  }

  return (
    <Card className='h-fit'>
      {/* 액션 메시지 */}
      {actionMessage && (
        <div className='mb-6'>
          <Alert type={actionMessage.type}>{actionMessage.text}</Alert>
        </div>
      )}

      <div className='mb-6'>
        <h2 className='text-xl font-bold text-gray-900 mb-2'>도서 열람 신청</h2>
        <p className='text-gray-600'>이 책을 읽으려면 도서 열람 신청이 필요합니다.</p>
        <div className='mt-4 p-4 bg-blue-50 rounded-lg'>
          <h3 className='font-semibold text-blue-900 mb-2'>신청 안내</h3>
          <ul className='text-sm text-blue-800 space-y-1'>
            <li>• 신청 후 관리자 승인을 기다려야 합니다</li>
            <li>• 승인 완료 시 알림을 받을 수 있습니다</li>
            <li>• 승인된 도서는 기간 내 자유롭게 열람 가능합니다</li>
          </ul>
        </div>
      </div>

      <Divider className='my-6' />

      <div className='text-center'>
        <Button
          onClick={handleSubscribeBook}
          loading={isSubscribeBookLoading}
          fullWidth
          variant='primary'
          disabled={actionMessage?.type === 'success'}
        >
          도서 열람 신청하기
        </Button>
      </div>

      {actionMessage?.type === 'success' && (
        <>
          <Divider className='my-6' />
          <div className='text-center'>
            <Button
              onClick={handleRetry}
              fullWidth
              variant='secondary'
            >
              다시 신청하기
            </Button>
          </div>
          <div className='text-sm text-gray-500 text-center mt-4'>
            <p>도서 열람 신청이 완료되었습니다.</p>
            <p className='mt-1'>관리자 승인 후 열람이 가능합니다.</p>
          </div>
        </>
      )}
    </Card>
  )
}

export default BookAccessRequest
