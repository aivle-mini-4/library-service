import { useDispatch } from 'react-redux'
import { useNavigate } from 'react-router-dom'
import { logout } from '../store/authSlice'

function Home() {
  const dispatch = useDispatch()
  const navigate = useNavigate()

  return (
    <div className='min-h-screen bg-gray-50 py-8 px-4'>
      <div className='max-w-4xl mx-auto'>
        <div className='bg-white rounded-lg shadow-sm p-6'>
          <h1 className='text-3xl font-bold text-gray-900 mb-6'>홈</h1>

          <div className='flex flex-col sm:flex-row gap-4'>
            <button
              onClick={() => navigate('/mypage')}
              className='inline-flex items-center justify-center px-6 py-3 border border-transparent text-base font-medium rounded-md text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 transition-colors duration-200'
            >
              마이페이지
            </button>

            <button
              onClick={() => {
                dispatch(logout())
                navigate('/login')
              }}
              className='inline-flex items-center justify-center px-6 py-3 border border-gray-300 text-base font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 transition-colors duration-200'
            >
              로그아웃
            </button>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Home
