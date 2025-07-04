import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { authApi } from '../api/auth'
import Alert from '../components/Alert'
import Button from '../components/Button'
import Card from '../components/Card'
import Input from '../components/Input'
import Textarea from '../components/Textarea'
import { useForm, validationRules } from '../hooks/useForm'

function Register() {
  const [userType, setUserType] = useState('user')
  const navigate = useNavigate()

  // 검증 규칙 정의
  const registerValidationRules = {
    email: value => validationRules.required(value) || validationRules.email(value),
    password: value => validationRules.required(value) || validationRules.minLength(6)(value),
    confirmPassword: value =>
      validationRules.required(value) || validationRules.passwordConfirm('password')(value, values),
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

  const { values, errors, isLoading, submitError, handleChange, handleSubmit } = useForm(
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

  const userTypeOptions = [
    { value: 'user', label: '일반 사용자', icon: '👤', color: 'bg-blue-200 border-blue-300 text-blue-700' },
    { value: 'author', label: '작가', icon: '✍️', color: 'bg-purple-200 border-purple-300 text-purple-700' },
    { value: 'admin', label: '관리자', icon: '⚙️', color: 'bg-orange-200 border-orange-300 text-orange-700' },
  ]

  return (
    <div className='min-h-screen flex items-center justify-center bg-blue-50 py-12 px-4 sm:px-6 lg:px-8'>
      <div className='max-w-md w-full'>
        <Card className='p-8'>
          <div className='text-center mb-8'>
            <div className='w-16 h-16 bg-slate-600 rounded-full flex items-center justify-center mx-auto mb-6'>
              <svg
                className='w-8 h-8 text-white'
                fill='none'
                stroke='currentColor'
                viewBox='0 0 24 24'
              >
                <path
                  strokeLinecap='round'
                  strokeLinejoin='round'
                  strokeWidth={2}
                  d='M18 9v3m0 0v3m0-3h3m-3 0h-3m-2-5a4 4 0 11-8 0 4 4 0 018 0zM3 20a6 6 0 0112 0v1H3v-1z'
                />
              </svg>
            </div>
            <h2 className='text-3xl font-bold text-gray-800 mb-2'>회원가입</h2>
            <p className='text-gray-600'>계정을 생성하고 서비스를 이용해보세요</p>
          </div>

          <form
            onSubmit={handleFormSubmit}
            className='space-y-6'
          >
            {/* 사용자 타입 선택 */}
            <div className='space-y-4'>
              <label className='block text-lg font-semibold text-gray-800 text-center'>계정 유형 선택</label>
              <div className='grid grid-cols-3 gap-3'>
                {userTypeOptions.map(option => (
                  <button
                    key={option.value}
                    type='button'
                    onClick={() => setUserType(option.value)}
                    className={`p-4 rounded-lg border-2 transition-all duration-200 ${
                      userType === option.value
                        ? `${option.color}`
                        : 'border-gray-200 bg-white text-gray-600 hover:border-gray-300 hover:bg-gray-50'
                    }`}
                  >
                    <div className='text-2xl mb-1'>{option.icon}</div>
                    <div className='text-sm font-medium'>{option.label}</div>
                  </button>
                ))}
              </div>
            </div>

            {/* 에러 메시지 */}
            {submitError && <Alert type='error'>{submitError}</Alert>}

            {/* 기본 정보 */}
            <div className='space-y-4'>
              <Input
                label='이메일 *'
                name='email'
                type='email'
                value={values.email}
                onChange={handleChange}
                error={errors.email}
                placeholder='example@email.com'
              />

              <Input
                label='비밀번호 *'
                name='password'
                type='password'
                value={values.password}
                onChange={handleChange}
                error={errors.password}
                placeholder='최소 6자 이상'
              />

              <Input
                label='비밀번호 확인 *'
                name='confirmPassword'
                type='password'
                value={values.confirmPassword}
                onChange={handleChange}
                error={errors.confirmPassword}
                placeholder='비밀번호를 다시 입력하세요'
              />

              {/* 작가 전용 필드 */}
              {userType === 'author' && (
                <div className='space-y-4 p-4 bg-purple-50 rounded-lg border border-purple-200'>
                  <div className='flex items-center space-x-2'>
                    <span className='text-lg font-semibold text-purple-800'>작가 정보</span>
                  </div>

                  <Textarea
                    label='작가 소개 *'
                    name='selfIntroduction'
                    value={values.selfIntroduction}
                    onChange={handleChange}
                    error={errors.selfIntroduction}
                    placeholder='자신을 소개해주세요'
                    rows={4}
                  />

                  <Textarea
                    label='포트폴리오'
                    name='portfolio'
                    value={values.portfolio}
                    onChange={handleChange}
                    placeholder='이전 작품이나 경력을 소개해주세요 (선택사항)'
                    rows={4}
                  />
                </div>
              )}
            </div>

            <Button
              type='submit'
              loading={isLoading}
              fullWidth
              variant='primary'
            >
              {isLoading ? '처리중...' : '회원가입'}
            </Button>

            <div className='text-center'>
              <span className='text-gray-600'>이미 계정이 있으신가요? </span>
              <button
                type='button'
                onClick={() => navigate('/login')}
                className='text-slate-600 hover:text-slate-500 font-medium ml-1 hover:underline'
              >
                로그인하기
              </button>
            </div>
          </form>
        </Card>
      </div>
    </div>
  )
}

export default Register
