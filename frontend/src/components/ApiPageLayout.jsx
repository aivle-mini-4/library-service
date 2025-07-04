import React from 'react'
import { Outlet, useNavigate } from 'react-router-dom'
import Alert from './Alert'
import Button from './Button'
import PageHeader from './PageHeader'

const LayoutWrapper = ({ children, showLayout, handleBack }) => {
  if (!showLayout) {
    return <>{children}</>
  }

  return (
    <div className='min-h-screen bg-blue-50 py-8 px-4'>
      <div className='max-w-4xl mx-auto'>
        <PageHeader onBack={handleBack} />
        {children}
      </div>
    </div>
  )
}

const ApiPageLayout = ({
  isLoading,
  error,
  isSuccess,
  onBack,
  onRetry,
  children,
  loadingMessage = '로딩 중...',
  errorMessage,
  showLayout = true,
}) => {
  const navigate = useNavigate()

  const handleBack = onBack || (() => navigate(-1))

  if (isLoading) {
    return (
      <LayoutWrapper
        showLayout={showLayout}
        handleBack={handleBack}
      >
        <div className='flex items-center justify-center py-12'>
          <div className='text-center'>
            <div className='animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600 mx-auto mb-4'></div>
            <p className='text-gray-600'>{loadingMessage}</p>
          </div>
        </div>
      </LayoutWrapper>
    )
  }

  if (error || !isSuccess) {
    return (
      <LayoutWrapper
        showLayout={showLayout}
        handleBack={handleBack}
      >
        <div className='py-8'>
          <Alert
            type='error'
            message={errorMessage || error || '오류가 발생했습니다.'}
          />
          {onRetry && (
            <div className='mt-6 text-center'>
              <Button
                onClick={onRetry}
                variant='secondary'
              >
                다시 시도
              </Button>
            </div>
          )}
        </div>
      </LayoutWrapper>
    )
  }

  return (
    <LayoutWrapper
      showLayout={showLayout}
      handleBack={handleBack}
    >
      {children || <Outlet />}
    </LayoutWrapper>
  )
}

export default ApiPageLayout
