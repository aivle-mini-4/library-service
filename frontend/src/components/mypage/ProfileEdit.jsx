import React, { useState, useEffect } from 'react'
import { useSelector } from 'react-redux'
import Button from '../Button'
import Input from '../Input'
import Textarea from '../Textarea'
import Alert from '../Alert'
import { mypageApi } from '../../api/mypage'

// JWT 토큰에서 사용자 ID를 추출하는 함수
const getUserIdFromToken = token => {
  try {
    if (!token) {
      console.error('토큰이 없습니다.')
      return null
    }

    // 실제 JWT 토큰이 아니라면, 다른 방법으로 사용자 ID를 가져와야 함
    // 예: 별도 API 호출, Redux store에서 가져오기 등

    // 임시로 1을 반환 (실제로는 로그인 시 사용자 ID를 저장해야 함)
    return '1'
  } catch (error) {
    console.error('토큰 처리 중 오류:', error)
    return null
  }
}

const ProfileEdit = () => {
  const { token } = useSelector(state => state.auth)
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    introduction: '',
  })
  const [userInfo, setUserInfo] = useState(null)
  const [isLoading, setIsLoading] = useState(true)
  const [isSubmitting, setIsSubmitting] = useState(false)
  const [message, setMessage] = useState({ type: '', text: '' })

  // 사용자 정보 조회
  useEffect(() => {
    fetchUserInfo()
  }, [])

  const fetchUserInfo = async () => {
    try {
      setIsLoading(true)
      const userId = getUserIdFromToken(token)

      if (!userId) {
        throw new Error('사용자 ID를 찾을 수 없습니다.')
      }

      console.log('사용자 ID:', userId)

      const response = await mypageApi.memberProfiles.getById(userId)
      const userData = response.data

      console.log('사용자 데이터:', userData)

      setUserInfo(userData)
      setFormData({
        name: userData.name || '',
        email: userData.email || '',
        introduction: userData.basicInfo || '',
      })
    } catch (error) {
      console.error('User info fetch error:', error)
      setMessage({
        type: 'error',
        text: '사용자 정보를 불러오는 중 오류가 발생했습니다.',
      })
    } finally {
      setIsLoading(false)
    }
  }

  const handleInputChange = e => {
    const { name, value } = e.target
    setFormData(prev => ({
      ...prev,
      [name]: value,
    }))
  }

  const handleSubmit = async e => {
    e.preventDefault()
    setIsSubmitting(true)
    setMessage({ type: '', text: '' })

    try {
      const userId = getUserIdFromToken(token)

      if (!userId) {
        throw new Error('사용자 ID를 찾을 수 없습니다.')
      }

      await mypageApi.memberProfiles.update(userId, formData)
      setMessage({ type: 'success', text: '프로필이 성공적으로 수정되었습니다.' })

      // 수정 후 사용자 정보 다시 조회
      await fetchUserInfo()
    } catch (error) {
      console.error('Profile update error:', error)
      setMessage({
        type: 'error',
        text: error.response?.data?.message || error.message || '프로필 수정 중 오류가 발생했습니다.',
      })
    } finally {
      setIsSubmitting(false)
    }
  }

  if (isLoading) {
    return (
      <div className='flex items-center justify-center py-12'>
        <div className='animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600'></div>
      </div>
    )
  }

  return (
    <div className='max-w-2xl'>
      <div className='mb-6'>
        <h2 className='text-lg font-semibold text-gray-900 mb-2'>내 정보 수정</h2>
        <p className='text-sm text-gray-600'>개인정보를 수정할 수 있습니다.</p>
      </div>

      {message.text && (
        <Alert
          type={message.type}
          message={message.text}
          className='mb-6'
        />
      )}

      {/* 사용자 정보 요약 */}
      {userInfo && (
        <div className='bg-gray-50 rounded-lg p-4 mb-6'>
          <h3 className='text-sm font-medium text-gray-700 mb-3'>계정 정보</h3>
          <div className='grid grid-cols-1 md:grid-cols-2 gap-4 text-sm'>
            <div>
              <span className='text-gray-500'>회원가입일:</span>
              <span className='ml-2 text-gray-900'>
                {userInfo.joinedAt ? new Date(userInfo.joinedAt).toLocaleDateString('ko-KR') : '정보 없음'}
              </span>
            </div>
            <div>
              <span className='text-gray-500'>마지막 수정일:</span>
              <span className='ml-2 text-gray-900'>
                {userInfo.lastUpdatedAt ? new Date(userInfo.lastUpdatedAt).toLocaleDateString('ko-KR') : '정보 없음'}
              </span>
            </div>
          </div>
        </div>
      )}

      <form
        onSubmit={handleSubmit}
        className='space-y-6'
      >
        <div>
          <label
            htmlFor='name'
            className='block text-sm font-medium text-gray-700 mb-2'
          >
            이름
          </label>
          <Input
            id='name'
            name='name'
            type='text'
            value={formData.name}
            onChange={handleInputChange}
            required
            placeholder='이름을 입력하세요'
          />
        </div>

        <div>
          <label
            htmlFor='email'
            className='block text-sm font-medium text-gray-700 mb-2'
          >
            이메일
          </label>
          <Input
            id='email'
            name='email'
            type='email'
            value={formData.email}
            onChange={handleInputChange}
            required
            placeholder='이메일을 입력하세요'
          />
        </div>

        <div>
          <label
            htmlFor='introduction'
            className='block text-sm font-medium text-gray-700 mb-2'
          >
            소개
          </label>
          <Textarea
            id='introduction'
            name='introduction'
            value={formData.introduction}
            onChange={handleInputChange}
            rows={4}
            placeholder='자기소개를 입력하세요'
          />
        </div>

        <div className='flex justify-end'>
          <Button
            type='submit'
            loading={isSubmitting}
            disabled={isSubmitting}
          >
            정보 수정
          </Button>
        </div>
      </form>
    </div>
  )
}

export default ProfileEdit
