import React, { useState, useEffect } from 'react'
import { useSelector } from 'react-redux'
import { mypageApi } from '../../api/mypage'
import Alert from '../Alert'
import Button from '../Button'

const Subscription = () => {
  const { token } = useSelector(state => state.auth)
  const [subscription, setSubscription] = useState(null)
  const [isLoading, setIsLoading] = useState(true)
  const [isActionLoading, setIsActionLoading] = useState(false)
  const [error, setError] = useState(null)
  const [message, setMessage] = useState({ type: '', text: '' })

  useEffect(() => {
    fetchSubscription()
  }, [])

  const fetchSubscription = async () => {
    try {
      setIsLoading(true)
      const response = await mypageApi.subscription.getSubscribeViews()
      setSubscription(response.data)
    } catch (error) {
      setError('구독 정보를 불러오는 중 오류가 발생했습니다.')
    } finally {
      setIsLoading(false)
    }
  }

  const handleSubscribe = async () => {
    try {
      setIsActionLoading(true)
      setMessage({ type: '', text: '' })

      await mypageApi.subscription.createSubscribe({})
      setMessage({ type: 'success', text: '구독이 성공적으로 시작되었습니다.' })
      fetchSubscription()
    } catch (error) {
      setMessage({
        type: 'error',
        text: error.response?.data?.message || '구독 시작 중 오류가 발생했습니다.',
      })
    } finally {
      setIsActionLoading(false)
    }
  }

  const handleUnsubscribe = async () => {
    if (!subscription?.id) return

    try {
      setIsActionLoading(true)
      setMessage({ type: '', text: '' })

      await mypageApi.subscription.deleteSubscribe(subscription.id)
      setMessage({ type: 'success', text: '구독이 성공적으로 취소되었습니다.' })
      fetchSubscription()
    } catch (error) {
      setMessage({
        type: 'error',
        text: error.response?.data?.message || '구독 취소 중 오류가 발생했습니다.',
      })
    } finally {
      setIsActionLoading(false)
    }
  }

  if (isLoading) {
    return (
      <div className='flex items-center justify-center py-12'>
        <div className='animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600'></div>
      </div>
    )
  }

  if (error) {
    return (
      <Alert
        type='error'
        message={error}
        action={
          <Button
            onClick={fetchSubscription}
            variant='secondary'
            size='sm'
          >
            다시 시도
          </Button>
        }
      />
    )
  }

  return (
    <div>
      <div className='mb-6'>
        <h2 className='text-lg font-semibold text-gray-900 mb-2'>구독 관리</h2>
        <p className='text-sm text-gray-600'>월간 구독 서비스를 관리할 수 있습니다.</p>
      </div>

      {message.text && (
        <Alert
          type={message.type}
          message={message.text}
          className='mb-6'
        />
      )}

      <div className='bg-white border border-gray-200 rounded-lg p-6'>
        <div className='flex items-center justify-between mb-4'>
          <div>
            <h3 className='text-lg font-medium text-gray-900'>월간 구독</h3>
            <p className='text-sm text-gray-500 mt-1'>모든 도서를 무제한으로 열람할 수 있습니다.</p>
          </div>
          <div className='text-right'>
            <div className='text-2xl font-bold text-gray-900'>
              {subscription?.isSubscribed ? '구독 중' : '구독 안함'}
            </div>
            {subscription?.isSubscribed && (
              <div className='text-sm text-gray-500'>
                만료일: {new Date(subscription.expiryDate).toLocaleDateString('ko-KR')}
              </div>
            )}
          </div>
        </div>

        <div className='flex justify-end space-x-3'>
          {subscription?.isSubscribed ? (
            <Button
              onClick={handleUnsubscribe}
              variant='danger'
              loading={isActionLoading}
              disabled={isActionLoading}
            >
              구독 취소
            </Button>
          ) : (
            <Button
              onClick={handleSubscribe}
              loading={isActionLoading}
              disabled={isActionLoading}
            >
              구독 시작
            </Button>
          )}
        </div>
      </div>
    </div>
  )
}

export default Subscription
