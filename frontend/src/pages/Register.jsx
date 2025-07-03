import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { authApi } from '../api/auth'
import { useForm, validationRules } from '../hooks/useForm'

function Register() {
  const [userType, setUserType] = useState('user') // user, author, admin
  const navigate = useNavigate()

  // 검증 규칙 정의
  const registerValidationRules = {
    email: validationRules.email,
    password: validationRules.minLength(6),
    confirmPassword: validationRules.passwordConfirm('password'),
    selfIntroduction: validationRules.requiredIf(() => userType === 'author'),
  }

  // 초기값 정의
  const initialValues = {
    email: '',
    password: '',
    confirmPassword: '',
    selfIntroduction: '',
    portfolio: '',
  }

  const { values, errors, isLoading, submitError, handleChange, setValue, handleSubmit } = useForm(
    initialValues,
    registerValidationRules,
  )

  const onSubmit = async formData => {
    const signupData = {
      email: formData.email,
      password: formData.password,
    }

    // 작가인 경우 추가 정보 포함
    if (userType === 'author') {
      signupData.selfIntroduction = formData.selfIntroduction
      signupData.portfolio = formData.portfolio
    }

    let response
    switch (userType) {
      case 'user':
        response = await authApi.userSignup(signupData)
        break
      case 'author':
        response = await authApi.authorSignup(signupData)
        break
      case 'admin':
        response = await authApi.adminSignup(signupData)
        break
      default:
        throw new Error('잘못된 사용자 타입입니다.')
    }

    if (response?.data) {
      alert('회원가입이 완료되었습니다!')
      navigate('/login')
    }
    return response
  }

  const handleFormSubmit = async e => {
    e.preventDefault()
    const success = await handleSubmit(onSubmit)
    if (success) {
      // 성공 시 추가 처리 (필요시)
    }
  }

  return (
    <div className='min-h-screen flex items-center justify-center bg-gray-50 py-12 px-4 sm:px-6 lg:px-8'>
      <div className='max-w-md w-full space-y-8'>
        <div>
          <h2 className='mt-6 text-center text-3xl font-extrabold text-gray-900'>회원가입</h2>
        </div>

        <form
          className='mt-8 space-y-6'
          onSubmit={handleFormSubmit}
        >
          {/* 사용자 타입 선택 */}
          <div className='space-y-4'>
            <label className='block text-sm font-medium text-gray-700'>계정 유형 선택</label>
            <div className='grid grid-cols-3 gap-3'>
              <button
                type='button'
                onClick={() => setUserType('user')}
                className={`py-2 px-4 border rounded-md text-sm font-medium ${
                  userType === 'user'
                    ? 'bg-blue-600 text-white border-blue-600'
                    : 'bg-white text-gray-700 border-gray-300 hover:bg-gray-50'
                }`}
              >
                일반 사용자
              </button>
              <button
                type='button'
                onClick={() => setUserType('author')}
                className={`py-2 px-4 border rounded-md text-sm font-medium ${
                  userType === 'author'
                    ? 'bg-blue-600 text-white border-blue-600'
                    : 'bg-white text-gray-700 border-gray-300 hover:bg-gray-50'
                }`}
              >
                작가
              </button>
              <button
                type='button'
                onClick={() => setUserType('admin')}
                className={`py-2 px-4 border rounded-md text-sm font-medium ${
                  userType === 'admin'
                    ? 'bg-blue-600 text-white border-blue-600'
                    : 'bg-white text-gray-700 border-gray-300 hover:bg-gray-50'
                }`}
              >
                관리자
              </button>
            </div>
          </div>

          {/* 에러 메시지 */}
          {submitError && (
            <div className='bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-md text-sm'>
              {submitError}
            </div>
          )}

          {/* 기본 정보 */}
          <div className='space-y-4'>
            <div>
              <label
                htmlFor='email'
                className='block text-sm font-medium text-gray-700'
              >
                이메일 *
              </label>
              <input
                id='email'
                name='email'
                type='email'
                required
                value={values.email}
                onChange={handleChange}
                className={`mt-1 block w-full px-3 py-2 border rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 ${
                  errors.email ? 'border-red-300' : 'border-gray-300'
                }`}
                placeholder='example@email.com'
              />
              {errors.email && <p className='mt-1 text-sm text-red-600'>{errors.email}</p>}
            </div>

            <div>
              <label
                htmlFor='password'
                className='block text-sm font-medium text-gray-700'
              >
                비밀번호 *
              </label>
              <input
                id='password'
                name='password'
                type='password'
                required
                value={values.password}
                onChange={handleChange}
                className={`mt-1 block w-full px-3 py-2 border rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 ${
                  errors.password ? 'border-red-300' : 'border-gray-300'
                }`}
                placeholder='최소 6자 이상'
              />
              {errors.password && <p className='mt-1 text-sm text-red-600'>{errors.password}</p>}
            </div>

            <div>
              <label
                htmlFor='confirmPassword'
                className='block text-sm font-medium text-gray-700'
              >
                비밀번호 확인 *
              </label>
              <input
                id='confirmPassword'
                name='confirmPassword'
                type='password'
                required
                value={values.confirmPassword}
                onChange={handleChange}
                className={`mt-1 block w-full px-3 py-2 border rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 ${
                  errors.confirmPassword ? 'border-red-300' : 'border-gray-300'
                }`}
                placeholder='비밀번호를 다시 입력하세요'
              />
              {errors.confirmPassword && <p className='mt-1 text-sm text-red-600'>{errors.confirmPassword}</p>}
            </div>

            {/* 작가 전용 필드 */}
            {userType === 'author' && (
              <>
                <div>
                  <label
                    htmlFor='selfIntroduction'
                    className='block text-sm font-medium text-gray-700'
                  >
                    작가 소개 *
                  </label>
                  <textarea
                    id='selfIntroduction'
                    name='selfIntroduction'
                    required
                    value={values.selfIntroduction}
                    onChange={handleChange}
                    rows={3}
                    className={`mt-1 block w-full px-3 py-2 border rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 ${
                      errors.selfIntroduction ? 'border-red-300' : 'border-gray-300'
                    }`}
                    placeholder='자신을 소개해주세요'
                  />
                  {errors.selfIntroduction && <p className='mt-1 text-sm text-red-600'>{errors.selfIntroduction}</p>}
                </div>

                <div>
                  <label
                    htmlFor='portfolio'
                    className='block text-sm font-medium text-gray-700'
                  >
                    포트폴리오
                  </label>
                  <textarea
                    id='portfolio'
                    name='portfolio'
                    value={values.portfolio}
                    onChange={handleChange}
                    rows={3}
                    className='mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500'
                    placeholder='이전 작품이나 경력을 소개해주세요 (선택사항)'
                  />
                </div>
              </>
            )}
          </div>

          <div>
            <button
              type='submit'
              disabled={isLoading}
              className='group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 disabled:opacity-50 disabled:cursor-not-allowed'
            >
              {isLoading ? '처리중...' : '회원가입'}
            </button>
          </div>

          <div className='text-center'>
            <span className='text-sm text-gray-600'>이미 계정이 있으신가요? </span>
            <button
              type='button'
              onClick={() => navigate('/login')}
              className='text-sm text-blue-600 hover:text-blue-500 font-medium'
            >
              로그인하기
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}

export default Register
