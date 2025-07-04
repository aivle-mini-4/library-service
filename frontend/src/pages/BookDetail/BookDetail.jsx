import { useParams } from 'react-router-dom'
import { subscriptionApi } from '../../api/subscription'
import ApiPageLayout from '../../components/ApiPageLayout'
import { useApi } from '../../hooks/useApi'
import BookAccessRequest from './components/BookAccessRequest'
import BookReader from './components/BookReader'

function BookDetail() {
  const { id } = useParams()

  const {
    isLoading: isSubscriptionLoading,
    error: subscriptionError,
    data: subscriptionData,
    isSuccess: isSubscriptionSuccess,
  } = useApi(subscriptionApi.monthlySubscription.getSubscription, { runOnMount: true })

  return (
    <div className='min-h-screen bg-blue-50 py-8 px-4'>
      <div className='max-w-4xl mx-auto'>
        <ApiPageLayout
          isLoading={isSubscriptionLoading}
          error={subscriptionError ? String(subscriptionError) : null}
          data={subscriptionData}
          isSuccess={isSubscriptionSuccess}
        >
          {subscriptionData && (
            <div className='grid grid-cols-1 lg:grid-cols-3 gap-8'>
              <div className='lg:col-span-3'>
                {subscriptionData?.isSubscribed ? (
                  <BookReader
                    bookId={id}
                    isSubscriber={true}
                  />
                ) : (
                  <BookAccessRequest bookId={id} />
                )}
              </div>
            </div>
          )}
        </ApiPageLayout>
      </div>
    </div>
  )
}

export default BookDetail
