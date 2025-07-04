import React, { useState, useEffect } from 'react'
import { mypageApi } from '../../api/mypage'
import Alert from '../Alert'
import Button from '../Button'

const Favorites = () => {
  const [favorites, setFavorites] = useState([])
  const [isLoading, setIsLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    fetchFavorites()
  }, [])

  const fetchFavorites = async () => {
    try {
      setIsLoading(true)
      const response = await mypageApi.favorites.getAll()
      setFavorites(response.data._embedded?.favorites || [])
    } catch (error) {
      setError('즐겨찾기 목록을 불러오는 중 오류가 발생했습니다.')
    } finally {
      setIsLoading(false)
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
            onClick={fetchFavorites}
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
        <h2 className='text-lg font-semibold text-gray-900 mb-2'>즐겨찾기</h2>
        <p className='text-sm text-gray-600'>즐겨찾기한 도서 목록입니다.</p>
      </div>

      {favorites.length === 0 ? (
        <div className='text-center py-12'>
          <p className='text-gray-500'>즐겨찾기한 도서가 없습니다.</p>
        </div>
      ) : (
        <div className='grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6'>
          {favorites.map(favorite => (
            <div
              key={favorite.id}
              className='bg-white border border-gray-200 rounded-lg overflow-hidden shadow-sm hover:shadow-md transition-shadow'
            >
              <div className='aspect-w-3 aspect-h-4 bg-gray-200'>
                {favorite.bookCoverUrl ? (
                  <img
                    src={favorite.bookCoverUrl}
                    alt={favorite.bookTitle}
                    className='w-full h-full object-cover'
                  />
                ) : (
                  <div className='flex items-center justify-center h-full'>
                    <span className='text-gray-400'>이미지 없음</span>
                  </div>
                )}
              </div>
              <div className='p-4'>
                <h3 className='font-medium text-gray-900 mb-1 line-clamp-2'>{favorite.bookTitle || '제목 없음'}</h3>
                <p className='text-sm text-gray-500 mb-2'>{favorite.authorName || '작가 정보 없음'}</p>
                <p className='text-xs text-gray-400'>
                  즐겨찾기 추가일: {new Date(favorite.createdDate).toLocaleDateString('ko-KR')}
                </p>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  )
}

export default Favorites
