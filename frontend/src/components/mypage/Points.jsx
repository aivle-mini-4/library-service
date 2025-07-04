import React, { useState, useEffect } from 'react'
import { mypageApi } from '../../api/mypage'
import Alert from '../Alert'
import Button from '../Button'

const Points = () => {
  const [points, setPoints] = useState([])
  const [pointViews, setPointViews] = useState([])
  const [currentBalance, setCurrentBalance] = useState(0)
  const [isLoading, setIsLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    fetchPointsData()
  }, [])

  const fetchPointsData = async () => {
    try {
      setIsLoading(true)
      const [pointsResponse, pointViewsResponse] = await Promise.all([
        mypageApi.points.getAll(),
        mypageApi.points.getPointViews(),
      ])

      setPoints(pointsResponse.data._embedded?.points || [])
      setPointViews(pointViewsResponse.data._embedded?.pointViews || [])

      // 현재 잔액 계산
      const balance = points.reduce((sum, point) => {
        return sum + (point.pointAmount || 0)
      }, 0)
      setCurrentBalance(balance)
    } catch (error) {
      setError('포인트 정보를 불러오는 중 오류가 발생했습니다.')
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
            onClick={fetchPointsData}
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
        <h2 className='text-lg font-semibold text-gray-900 mb-2'>포인트 관리</h2>
        <p className='text-sm text-gray-600'>포인트 잔액과 적립/사용 내역을 확인할 수 있습니다.</p>
      </div>

      {/* 현재 잔액 */}
      <div className='bg-white border border-gray-200 rounded-lg p-6 mb-6'>
        <div className='text-center'>
          <h3 className='text-lg font-medium text-gray-900 mb-2'>현재 포인트 잔액</h3>
          <div className='text-3xl font-bold text-blue-600'>{currentBalance.toLocaleString()} P</div>
        </div>
      </div>

      {/* 포인트 내역 */}
      <div className='bg-white border border-gray-200 rounded-lg'>
        <div className='px-6 py-4 border-b border-gray-200'>
          <h3 className='text-lg font-medium text-gray-900'>포인트 내역</h3>
        </div>

        {pointViews.length === 0 ? (
          <div className='text-center py-12'>
            <p className='text-gray-500'>포인트 내역이 없습니다.</p>
          </div>
        ) : (
          <div className='overflow-x-auto'>
            <table className='min-w-full divide-y divide-gray-200'>
              <thead className='bg-gray-50'>
                <tr>
                  <th className='px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider'>
                    날짜
                  </th>
                  <th className='px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider'>
                    구분
                  </th>
                  <th className='px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider'>
                    포인트
                  </th>
                  <th className='px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider'>
                    설명
                  </th>
                </tr>
              </thead>
              <tbody className='bg-white divide-y divide-gray-200'>
                {pointViews.map(pointView => (
                  <tr
                    key={pointView.id}
                    className='hover:bg-gray-50'
                  >
                    <td className='px-6 py-4 whitespace-nowrap text-sm text-gray-900'>
                      {new Date(pointView.createdDate).toLocaleDateString('ko-KR')}
                    </td>
                    <td className='px-6 py-4 whitespace-nowrap'>
                      <span
                        className={`inline-flex px-2 py-1 text-xs font-semibold rounded-full ${
                          pointView.pointType === 'EARN' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'
                        }`}
                      >
                        {pointView.pointType === 'EARN' ? '적립' : '사용'}
                      </span>
                    </td>
                    <td className='px-6 py-4 whitespace-nowrap text-sm text-gray-900'>
                      {pointView.pointAmount?.toLocaleString()} P
                    </td>
                    <td className='px-6 py-4 text-sm text-gray-500'>{pointView.description || '-'}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  )
}

export default Points
