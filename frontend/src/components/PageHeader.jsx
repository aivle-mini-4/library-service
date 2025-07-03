function PageHeader({ onBack }) {
  return (
    <div className='mb-8'>
      <button
        onClick={onBack}
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
  )
}

export default PageHeader
