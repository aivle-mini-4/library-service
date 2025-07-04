import React, { useState, useEffect } from 'react'
import { mypageApi } from '../../api/mypage'
import Alert from '../Alert'
import Button from '../Button'

const ViewHistory = () => {
  const [histories, setHistories] = useState([])
  const [isLoading, setIsLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    fetchViewHistories()
  }, [])

  const fetchViewHistories = async () => {
    try {
      setIsLoading(true)
      const response = await mypageApi.viewHistories.getAll()
      setHistories(response.data._embedded?.viewHistories || [])
    } catch (error) {
      setError('열람 이력을 불러오는 중 오류가 발생했습니다.')
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
            onClick={fetchViewHistories}
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
        <h2 className='text-lg font-semibold text-gray-900 mb-2'>열람 이력</h2>
        <p className='text-sm text-gray-600'>최근에 열람한 도서 목록입니다.</p>
      </div>

      {histories.length === 0 ? (
        <div className='text-center py-12'>
          <p className='text-gray-500'>열람한 도서가 없습니다.</p>
        </div>
      ) : (
        <div className='overflow-x-auto'>
          <table className='min-w-full divide-y divide-gray-200'>
            <thead className='bg-gray-50'>
              <tr>
                <th className='px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider'>
                  도서명
                </th>
                <th className='px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider'>작가</th>
                <th className='px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider'>
                  열람일시
                </th>
              </tr>
            </thead>
            <tbody className='bg-white divide-y divide-gray-200'>
              {histories.map(history => (
                <tr
                  key={history.id}
                  className='hover:bg-gray-50'
                >
                  <td className='px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900'>
                    {history.bookTitle || '제목 없음'}
                  </td>
                  <td className='px-6 py-4 whitespace-nowrap text-sm text-gray-500'>
                    {history.authorName || '작가 정보 없음'}
                  </td>
                  <td className='px-6 py-4 whitespace-nowrap text-sm text-gray-500'>
                    {new Date(history.viewDate).toLocaleString('ko-KR')}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  )
}

export default ViewHistory
