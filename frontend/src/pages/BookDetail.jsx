import { useNavigate, useParams } from 'react-router-dom'
import Alert from '../components/Alert'
import Badge from '../components/Badge'
import Button from '../components/Button'
import Card from '../components/Card'
import Divider from '../components/Divider'
import { useBookDetail } from '../hooks/useBookDetail'

function BookDetail() {
  const { id } = useParams()
  const navigate = useNavigate()

  const { book, isSubscriber, loading, error, actionLoading, actionMessage, handleViewBook, handleSubscribe } =
    useBookDetail(id)

  if (loading) {
    return (
      <div className='min-h-screen flex items-center justify-center bg-blue-50'>
        <div className='text-center'>
          <div className='animate-spin rounded-full h-12 w-12 border-b-2 border-slate-600 mx-auto mb-4'></div>
          <p className='text-gray-600'>책 정보를 불러오는 중...</p>
        </div>
      </div>
    )
  }

  if (error) {
    return (
      <div className='min-h-screen flex items-center justify-center bg-blue-50 px-4'>
        <Card className='max-w-md w-full'>
          <Alert type='error'>{error}</Alert>
          <div className='mt-4 text-center'>
            <Button
              onClick={() => navigate('/')}
              variant='primary'
            >
              홈으로 돌아가기
            </Button>
          </div>
        </Card>
      </div>
    )
  }

  if (!book) {
    return (
      <div className='min-h-screen flex items-center justify-center bg-blue-50 px-4'>
        <Card className='max-w-md w-full'>
          <Alert type='error'>책을 찾을 수 없습니다.</Alert>
          <div className='mt-4 text-center'>
            <Button
              onClick={() => navigate('/')}
              variant='primary'
            >
              홈으로 돌아가기
            </Button>
          </div>
        </Card>
      </div>
    )
  }

  return (
    <div className='min-h-screen bg-blue-50 py-8 px-4'>
      <div className='max-w-4xl mx-auto'>
        {/* 헤더 */}
        <div className='mb-8'>
          <button
            onClick={() => navigate(-1)}
            className='flex items-center text-slate-600 hover:text-slate-800 mb-4'
          >
            <svg
              className='w-5 h-5 mr-2'
              fill='none'
              stroke='currentColor'
              viewBox='0 0 24 24'
            >
              <path
                strokeLinecap='round'
                strokeLinejoin='round'
                strokeWidth={2}
                d='M15 19l-7-7 7-7'
              />
            </svg>
            뒤로 가기
          </button>
          <h1 className='text-3xl font-bold text-gray-900'>책 상세 정보</h1>
        </div>

        {/* 액션 메시지 */}
        {actionMessage && (
          <div className='mb-6'>
            <Alert type={actionMessage.type}>{actionMessage.text}</Alert>
          </div>
        )}

        <div className='grid grid-cols-1 lg:grid-cols-3 gap-8'>
          {/* 책 정보 카드 */}
          <div className='lg:col-span-2'>
            <Card className='h-fit'>
              <div className='flex flex-col md:flex-row gap-6'>
                {/* 책 표지 */}
                <div className='flex-shrink-0'>
                  <div className='w-48 h-64 bg-gray-200 rounded-lg flex items-center justify-center'>
                    {book.coverImage ? (
                      <img
                        src={book.coverImage}
                        alt={book.title}
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

                {/* 책 정보 */}
                <div className='flex-1'>
                  <div className='mb-4'>
                    <h2 className='text-2xl font-bold text-gray-900 mb-2'>{book.title}</h2>
                    <p className='text-gray-600 mb-3'>저자: {book.author}</p>

                    <div className='flex flex-wrap gap-2 mb-4'>
                      {book.category && <Badge variant='primary'>{book.category}</Badge>}
                      {book.status && (
                        <Badge variant={book.status === 'PUBLISHED' ? 'success' : 'warning'}>{book.status}</Badge>
                      )}
                    </div>
                  </div>

                  <Divider className='my-4' />

                  <div className='space-y-3'>
                    {book.publisher && (
                      <div>
                        <span className='font-semibold text-gray-700'>출판사:</span>
                        <span className='ml-2 text-gray-600'>{book.publisher}</span>
                      </div>
                    )}
                    {book.publicationDate && (
                      <div>
                        <span className='font-semibold text-gray-700'>출판일:</span>
                        <span className='ml-2 text-gray-600'>
                          {new Date(book.publicationDate).toLocaleDateString()}
                        </span>
                      </div>
                    )}
                    {book.isbn && (
                      <div>
                        <span className='font-semibold text-gray-700'>ISBN:</span>
                        <span className='ml-2 text-gray-600'>{book.isbn}</span>
                      </div>
                    )}
                    {book.pageCount && (
                      <div>
                        <span className='font-semibold text-gray-700'>페이지:</span>
                        <span className='ml-2 text-gray-600'>{book.pageCount}페이지</span>
                      </div>
                    )}
                  </div>

                  {book.description && (
                    <>
                      <Divider className='my-4' />
                      <div>
                        <h3 className='font-semibold text-gray-700 mb-2'>책 소개</h3>
                        <p className='text-gray-600 leading-relaxed'>{book.description}</p>
                      </div>
                    </>
                  )}
                </div>
              </div>
            </Card>
          </div>

          {/* 구독 정보 및 액션 카드 */}
          <div className='lg:col-span-1'>
            <Card>
              <div className='text-center mb-6'>
                <div className='w-16 h-16 bg-slate-600 rounded-full flex items-center justify-center mx-auto mb-4'>
                  <svg
                    className='w-8 h-8 text-white'
                    fill='none'
                    stroke='currentColor'
                    viewBox='0 0 24 24'
                  >
                    <path
                      strokeLinecap='round'
                      strokeLinejoin='round'
                      strokeWidth={2}
                      d='M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.746 0 3.332.477 4.5 1.253v13C19.832 18.477 18.246 18 16.5 18c-1.746 0-3.332.477-4.5 1.253'
                    />
                  </svg>
                </div>
                <h3 className='text-xl font-bold text-gray-900 mb-2'>구독 정보</h3>
                <p className='text-gray-600'>{isSubscriber ? '월 구독자입니다' : '비구독자입니다'}</p>
              </div>

              <Divider className='my-6' />

              <div className='space-y-4'>
                {isSubscriber ? (
                  <div>
                    <p className='text-sm text-gray-600 mb-4'>월 구독자이므로 이 책을 자유롭게 열람할 수 있습니다.</p>
                    <Button
                      onClick={handleViewBook}
                      loading={actionLoading}
                      fullWidth
                      variant='primary'
                    >
                      책 열람하기
                    </Button>
                  </div>
                ) : (
                  <div>
                    <p className='text-sm text-gray-600 mb-4'>
                      이 책을 읽으려면 월 구독이 필요합니다. 알람 신청을 통해 구독 알림을 받을 수 있습니다.
                    </p>
                    <Button
                      onClick={handleSubscribe}
                      loading={actionLoading}
                      fullWidth
                      variant='primary'
                    >
                      알람 신청하기
                    </Button>
                  </div>
                )}
              </div>

              <Divider className='my-6' />

              <div className='text-center'>
                <p className='text-xs text-gray-500 mb-2'>월 구독 혜택</p>
                <ul className='text-xs text-gray-600 space-y-1'>
                  <li>• 모든 도서 무제한 열람</li>
                  <li>• 새로운 도서 알림</li>
                  <li>• 독서 기록 관리</li>
                </ul>
              </div>
            </Card>
          </div>
        </div>
      </div>
    </div>
  )
}

export default BookDetail
