import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { subscriptionApi } from '../../api/subscription'
import { useApi } from '../../hooks/useApi'
import { LoadingState, PageHeader } from './components'
import { ApiPageLayout } from './components/ApiPageLayout'
import BookAccessRequest from './components/BookAccessRequest'
import BookReader from './components/BookReader'

function BookDetail() {
  const { id } = useParams()
  const navigate = useNavigate()
  const [isSubscriber, setIsSubscriber] = useState(false)

  // API 훅 - 구조분해할당 사용
  const {
    isLoading: isSubscriptionLoading,
    error: subscriptionError,
    data: subscriptionData,
    isSuccess: isSubscriptionSuccess,
  } = useApi(subscriptionApi.monthlySubscription.getSubscription, { runOnMount: true })

  // 뒤로 가기
  const handleBack = () => navigate(-1)

  // 구독 상태 설정
  useEffect(() => {
    if (isSubscriptionSuccess && subscriptionData) {
      const { isSubscribed = false } = subscriptionData
      setIsSubscriber(isSubscribed)
    }
  }, [isSubscriptionSuccess, subscriptionData])

  // 로딩 상태
  if (isSubscriptionLoading) {
    return <LoadingState />
  }

  return (
    <div className='min-h-screen bg-blue-50 py-8 px-4'>
      <div className='max-w-4xl mx-auto'>
        <PageHeader onBack={handleBack} />
        <ApiPageLayout
          isLoading={isSubscriptionLoading}
          error={subscriptionError}
          data={subscriptionData}
          isSuccess={isSubscriptionSuccess}
        >
          <div className='grid grid-cols-1 lg:grid-cols-3 gap-8'>
            <div className='lg:col-span-3'>
              {isSubscriber ? <BookReader bookId={id} /> : <BookAccessRequest bookId={id} />}
            </div>
          </div>
        </ApiPageLayout>
      </div>
    </div>
  )
}

export default BookDetail
