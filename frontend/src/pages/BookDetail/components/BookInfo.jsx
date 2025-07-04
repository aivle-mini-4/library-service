import { booksApi } from '../../../api/books'
import ApiPageLayout from '../../../components/ApiPageLayout'
import Badge from '../../../components/Badge'
import Card from '../../../components/Card'
import Divider from '../../../components/Divider'
import { useApi } from '../../../hooks/useApi'

function BookInfo({ bookId }) {
  const {
    isLoading: isBookLoading,
    error: bookError,
    data: bookData,
    isSuccess: isBookSuccess,
    execute: executeBookQuery,
  } = useApi(booksApi.getBook, { params: [bookId], runOnMount: true })

  return (
    <ApiPageLayout
      isLoading={isBookLoading}
      error={bookError ? String(bookError) : null}
      isSuccess={isBookSuccess}
      loadingMessage='도서 정보를 불러오는 중...'
      errorMessage={bookError ? String(bookError) : '도서 정보를 불러올 수 없습니다.'}
      onRetry={executeBookQuery}
      showLayout={false}
    >
      {bookData?.result && (
        <Card className='h-fit'>
          <div className='flex flex-col md:flex-row gap-6'>
            {/* 책 표지 */}
            <div className='flex-shrink-0'>
              <div className='w-48 h-64 bg-gray-200 rounded-lg flex items-center justify-center'>
                {bookData.result.coverImageUrl ? (
                  <img
                    src={bookData.result.coverImageUrl}
                    alt={bookData.result.title}
                    className='w-full h-full object-cover rounded-lg'
                  />
                ) : (
                  <div className='text-gray-400 text-center'>
                    <svg
                      className='w-16 h-16 mx-auto mb-2'
                      fill='currentColor'
                      viewBox='0 0 20 20'
                    >
                      <path
                        fillRule='evenodd'
                        d='M4 3a2 2 0 00-2 2v10a2 2 0 002 2h12a2 2 0 002-2V5a2 2 0 00-2-2H4zm12 12H4l4-8 3 6 2-4 3 6z'
                        clipRule='evenodd'
                      />
                    </svg>
                    <p className='text-sm'>표지 이미지 없음</p>
                  </div>
                )}
              </div>
            </div>

            <div className='flex-1'>
              <div className='mb-4'>
                <h2 className='text-2xl font-bold text-gray-900 mb-2'>{bookData.result.title}</h2>
                <p className='text-gray-600 mb-3'>저자 ID: {bookData.result.authorId}</p>

                <div className='flex flex-wrap gap-2 mb-4'>
                  {bookData.result.category && <Badge variant='primary'>{bookData.result.category}</Badge>}
                  {bookData.result.price && <Badge variant='success'>{bookData.result.price.toLocaleString()}원</Badge>}
                  {bookData.result.views && <Badge variant='warning'>조회수 {bookData.result.views}</Badge>}
                </div>
              </div>

              <Divider className='my-4' />

              <div className='space-y-3'>
                {bookData.result.createdAt && (
                  <div>
                    <span className='font-semibold text-gray-700'>등록일:</span>
                    <span className='ml-2 text-gray-600'>
                      {new Date(bookData.result.createdAt).toLocaleDateString()}
                    </span>
                  </div>
                )}
                {bookData.result.updatedAt && (
                  <div>
                    <span className='font-semibold text-gray-700'>수정일:</span>
                    <span className='ml-2 text-gray-600'>
                      {new Date(bookData.result.updatedAt).toLocaleDateString()}
                    </span>
                  </div>
                )}
              </div>

              {bookData.result.summary && (
                <>
                  <Divider className='my-4' />
                  <div>
                    <h3 className='font-semibold text-gray-700 mb-2'>책 소개</h3>
                    <p className='text-gray-600 leading-relaxed'>{bookData.result.summary}</p>
                  </div>
                </>
              )}
            </div>
          </div>
        </Card>
      )}
    </ApiPageLayout>
  )
}

export default BookInfo
