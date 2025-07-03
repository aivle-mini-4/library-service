import React from 'react'
import { Outlet, useNavigate } from 'react-router-dom'
import Alert from './Alert'
import PageHeader from './PageHeader'

const ApiPageLayout = ({
  isLoading,
  error,
  isSuccess,
  onBack,
  children,
  loadingMessage = '로딩 중...',
  errorMessage,
}) => {
  const navigate = useNavigate()

  const handleBack = onBack || (() => navigate(-1))

  if (isLoading) {
    return (
      <div className='min-h-screen bg-blue-50 py-8 px-4'>
        <div className='max-w-4xl mx-auto'>
          <PageHeader onBack={handleBack} />
          <div className='flex items-center justify-center py-12'>
            <div className='text-center'>
              <div className='animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600 mx-auto mb-4'></div>
              <p className='text-gray-600'>{loadingMessage}</p>
            </div>
          </div>
        </div>
      </div>
    )
  }

  if (error || !isSuccess) {
    return (
      <div className='min-h-screen bg-blue-50 py-8 px-4'>
        <div className='max-w-4xl mx-auto'>
          <PageHeader onBack={handleBack} />
          <div className='py-8'>
            <Alert
              type='error'
              message={errorMessage || error || '오류가 발생했습니다.'}
            />
          </div>
        </div>
      </div>
    )
  }

  return (
    <div className='min-h-screen bg-blue-50 py-8 px-4'>
      <div className='max-w-4xl mx-auto'>
        <PageHeader onBack={handleBack} />
        {children || <Outlet />}
      </div>
    </div>
  )
}

export default ApiPageLayout
