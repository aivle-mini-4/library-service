import Badge from '../../../components/Badge'
import Card from '../../../components/Card'
import Divider from '../../../components/Divider'

function BookInfo({ book }) {
  return (
    <Card className='h-fit'>
      <div className='flex flex-col md:flex-row gap-6'>
        {/* 책 표지 */}
        <div className='flex-shrink-0'>
          <div className='w-48 h-64 bg-gray-200 rounded-lg flex items-center justify-center'>
            {book.coverImageUrl ? (
              <img
                src={book.coverImageUrl}
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
            <p className='text-gray-600 mb-3'>저자 ID: {book.authorId}</p>

            <div className='flex flex-wrap gap-2 mb-4'>
              {book.category && <Badge variant='primary'>{book.category}</Badge>}
              {book.price && <Badge variant='success'>{book.price.toLocaleString()}원</Badge>}
              {book.views && <Badge variant='warning'>조회수 {book.views}</Badge>}
            </div>
          </div>

          <Divider className='my-4' />

          <div className='space-y-3'>
            {book.createdAt && (
              <div>
                <span className='font-semibold text-gray-700'>등록일:</span>
                <span className='ml-2 text-gray-600'>{new Date(book.createdAt).toLocaleDateString()}</span>
              </div>
            )}
            {book.updatedAt && (
              <div>
                <span className='font-semibold text-gray-700'>수정일:</span>
                <span className='ml-2 text-gray-600'>{new Date(book.updatedAt).toLocaleDateString()}</span>
              </div>
            )}
          </div>

          {book.summary && (
            <>
              <Divider className='my-4' />
              <div>
                <h3 className='font-semibold text-gray-700 mb-2'>책 소개</h3>
                <p className='text-gray-600 leading-relaxed'>{book.summary}</p>
              </div>
            </>
          )}
        </div>
      </div>
    </Card>
  )
}

export default BookInfo
