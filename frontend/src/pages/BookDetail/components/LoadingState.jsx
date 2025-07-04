function LoadingState() {
  return (
    <div className='min-h-screen flex items-center justify-center bg-blue-50'>
      <div className='text-center'>
        <div className='animate-spin rounded-full h-12 w-12 border-b-2 border-slate-600 mx-auto mb-4'></div>
        <p className='text-gray-600'>책 정보를 불러오는 중...</p>
      </div>
    </div>
  )
}

export default LoadingState
