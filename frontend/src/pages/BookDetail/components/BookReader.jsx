import { subscriptionApi } from '../../../api/subscription'
import ApiPageLayout from '../../../components/ApiPageLayout'
import { useApi } from '../../../hooks/useApi'
import BookInfo from './BookInfo'

function BookReader({ bookId, isSubscriber }) {
  // isSubscriber에 따라 다른 API 호출 (runOnMount: true로 설정하여 마운트 시 자동 호출)
  const {
    isLoading: isViewBookLoading,
    error: viewBookError,
    data: viewBookData,
    isSuccess: isViewBookSuccess,
    execute: executeViewBook,
  } = useApi(
    isSubscriber
      ? subscriptionApi.bookSubscription.viewBook // 월구독자 도서 열람
      : subscriptionApi.bookSubscription.subscribeBook, // 비구독자 도서 열람 신청
    {
      runOnMount: true,
      params: [{ userId: 1, isBookSubscribed: isSubscriber }],
    },
  )

  return (
    <ApiPageLayout
      isLoading={isViewBookLoading}
      error={viewBookError ? String(viewBookError) : null}
      isSuccess={true}
      loadingMessage={isSubscriber ? '도서를 열람하는 중입니다...' : '도서 열람을 신청하는 중입니다...'}
      onRetry={executeViewBook}
      showLayout={false}
    >
      {viewBookData && (
        <div className='grid grid-cols-1 gap-8'>
          <div>
            <BookInfo bookId={bookId} />
          </div>
        </div>
      )}
    </ApiPageLayout>
  )
}

export default BookReader
