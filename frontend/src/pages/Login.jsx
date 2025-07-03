import { useDispatch } from 'react-redux'
import { useNavigate } from 'react-router-dom'
import { authApi } from '../api/auth'
import { useForm, validationRules } from '../hooks/useForm'
import { login } from '../store/authSlice'

function Login() {
  const navigate = useNavigate()
  const dispatch = useDispatch()

  // 검증 규칙 정의
  const loginValidationRules = {
    loginId: validationRules.required,
    password: validationRules.required,
  }

  // 초기값 정의
  const initialValues = {
    loginId: '',
    password: '',
  }

  const { values, errors, isLoading, submitError, handleChange, handleSubmit } = useForm(
    initialValues,
    loginValidationRules,
  )

  const onSubmit = async formData => {
    const response = await authApi.login({
      email: formData.loginId,
      password: formData.password,
    })

    if (response?.data) {
      dispatch(
        login({
          user: response.data.userId,
          token: response.data.token,
        }),
      )
      navigate('/')
    }
    return response
  }

  const handleFormSubmit = async e => {
    e.preventDefault()
    const success = await handleSubmit(onSubmit)
    if (!success) {
      // 실패 시 추가 처리 (필요시)
    }
  }

  return (
    <div className='min-h-screen flex items-center justify-center bg-gray-50 py-12 px-4 sm:px-6 lg:px-8'>
      <div className='max-w-md w-full space-y-8'>
        <div>
          <h2 className='mt-6 text-center text-3xl font-extrabold text-gray-900'>로그인</h2>
        </div>

        <form
          className='mt-8 space-y-6'
          onSubmit={handleFormSubmit}
        >
          {/* 에러 메시지 */}
          {submitError && (
            <div className='bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-md text-sm'>
              {submitError}
            </div>
          )}

          <div className='space-y-4'>
            <div>
              <label
                htmlFor='loginId'
                className='block text-sm font-medium text-gray-700'
              >
                아이디
              </label>
              <input
                id='loginId'
                name='loginId'
                type='text'
                value={values.loginId}
                onChange={handleChange}
                className={`mt-1 block w-full px-3 py-2 border rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 ${
                  errors.loginId ? 'border-red-300' : 'border-gray-300'
                }`}
                placeholder='아이디를 입력하세요'
              />
              {errors.loginId && <p className='mt-1 text-sm text-red-600'>{errors.loginId}</p>}
            </div>
            <div>
              <label
                htmlFor='password'
                className='block text-sm font-medium text-gray-700'
              >
                비밀번호
              </label>
              <input
                id='password'
                name='password'
                type='password'
                value={values.password}
                onChange={handleChange}
                className={`mt-1 block w-full px-3 py-2 border rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 ${
                  errors.password ? 'border-red-300' : 'border-gray-300'
                }`}
                placeholder='비밀번호를 입력하세요'
              />
              {errors.password && <p className='mt-1 text-sm text-red-600'>{errors.password}</p>}
            </div>
          </div>

          <div>
            <button
              type='submit'
              disabled={isLoading}
              className='group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 disabled:opacity-50 disabled:cursor-not-allowed'
            >
              {isLoading ? '로그인 중...' : '로그인'}
            </button>
          </div>

          <div className='text-center'>
            <span className='text-sm text-gray-600'>계정이 없으신가요? </span>
            <button
              type='button'
              onClick={() => navigate('/register')}
              className='text-sm text-blue-600 hover:text-blue-500 font-medium'
            >
              회원가입하기
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}

export default Login
