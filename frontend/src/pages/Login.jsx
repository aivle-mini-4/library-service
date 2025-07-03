import { useDispatch } from 'react-redux'
import { useNavigate } from 'react-router-dom'
import { authApi } from '../api/auth'
import Alert from '../components/ui/Alert'
import Button from '../components/ui/Button'
import Card from '../components/ui/Card'
import Input from '../components/ui/Input'
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
                  d='M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z'
                />
              </svg>
            </div>
            <h2 className='text-3xl font-bold text-gray-800 mb-2'>로그인</h2>
            <p className='text-gray-600'>계정에 로그인하여 서비스를 이용하세요</p>
          </div>

          <form
            onSubmit={handleFormSubmit}
            className='space-y-6'
          >
            {/* 에러 메시지 */}
            {submitError && <Alert type='error'>{submitError}</Alert>}

            <div className='space-y-4'>
              <Input
                label='아이디'
                name='loginId'
                type='text'
                value={values.loginId}
                onChange={handleChange}
                error={errors.loginId}
                placeholder='아이디를 입력하세요'
              />

              <Input
                label='비밀번호'
                name='password'
                type='password'
                value={values.password}
                onChange={handleChange}
                error={errors.password}
                placeholder='비밀번호를 입력하세요'
              />
            </div>

            <Button
              type='submit'
              loading={isLoading}
              fullWidth
              variant='primary'
            >
              {isLoading ? '로그인 중...' : '로그인'}
            </Button>

            <div className='text-center'>
              <span className='text-gray-600'>계정이 없으신가요? </span>
              <button
                type='button'
                onClick={() => navigate('/register')}
                className='text-slate-600 hover:text-slate-500 font-medium ml-1 hover:underline'
              >
                회원가입하기
              </button>
            </div>
          </form>
        </Card>
      </div>
    </div>
  )
}

export default Login
