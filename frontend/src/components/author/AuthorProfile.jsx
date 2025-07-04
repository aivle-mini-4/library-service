import React, { useState, useEffect } from 'react'
import { userInfoApi } from '../../api/userInfo'
import CardContainer from './common/CardContainer'
import Label from './common/Label'
import TextInput from './common/TextInput'
import TextArea from './common/TextArea'
import ActionButton from './common/ActionButton'
import ReadOnlyField from './common/ReadOnlyField'

function AuthorProfile() {
  const [name, setName] = useState('')
  const [email, setEmail] = useState('')
  const [basicInfo, setBasicInfo] = useState('')
  const [introduction, setIntroduction] = useState('')
  const [portfolioUrl, setPortfolioUrl] = useState('')
  const [isEditing, setIsEditing] = useState(false)
  
  useEffect(() => {
    // API로 작가 정보 받아오기
    userInfoApi.profiles.authorProfile.get()
      .then(res => {
        const data = res.data
        setName(data.name)
        setEmail(data.email)
        setBasicInfo(data.basicInformation)
        setIntroduction(data.selfIntroduction)
        setPortfolioUrl(data.portfolio)
      })
      .catch(err => {
        alert('작가 정보를 불러오지 못했습니다.')
      })
  }, [])

  const handleEdit = () => {
    setIsEditing(!isEditing)
  }

  const handleSave = () => {
    const data = {
        'name': name,
        'email': email,
        'roles': 'AUTHOR',
        'basicInformation': basicInfo,
        'selfIntroduction': introduction,
        'portfolio': portfolioUrl
    }
    userInfoApi.profiles.authorProfile.update(data)
    alert('저장되었습니다!')
    setIsEditing(false)
  }

  return (
    <CardContainer style={{marginBottom: '52px'}}>
        <ActionButton 
          style={{position: 'absolute', right: '48px', fontSize: '28px', padding: '8px 16px', marginTop: '8px'}}
          color={isEditing ? '#28a745' : '#007bff'}
          onClick={isEditing ? handleSave : handleEdit}
        >
          {isEditing ? '저장' : '수정'}
        </ActionButton>
        <h1 style={{fontSize: '52px'}}>작가 프로필</h1>
        
        {/* 이름 섹션 */}
        <h2 style={{fontSize: '48px'}}>
          {isEditing ? (
            <TextInput 
              value={name} 
              onChange={(e) => setName(e.target.value)}
              style={{fontSize: '48px', border: 'none', borderBottom: '2px solid #ccc', outline: 'none'}}
            />
          ) : (
            name
          )}
          <span style={{fontSize: '32px', color: '#888', marginLeft: '8px'}}>작가</span>
        </h2>
        <p style={{ fontSize: '24px', color: '#555'}}>
             {email}
        </p>
        
        {/* 기본 정보 섹션 */}
        <Label>기본 정보</Label>
        <div style={{border: '1px solid #ccc', borderRadius: '20px', display: 'flex', flexDirection: 'column'}}>
          {isEditing ? (
            <TextInput 
              value={basicInfo} 
              onChange={(e) => setBasicInfo(e.target.value)}
            />
          ) : (
            <ReadOnlyField>{basicInfo}</ReadOnlyField>
          )}
        </div>
        
        {/* 자기소개 섹션 */}
        <Label style={{marginBottom: '12px'}}>자기소개</Label>
        <div style={{border: '1px solid #ccc', borderRadius: '20px', display: 'flex', flexDirection: 'column'}}>
          {isEditing ? (
            <TextArea 
              value={introduction} 
              onChange={(e) => setIntroduction(e.target.value)}
            />
          ) : (
            <ReadOnlyField>{introduction}</ReadOnlyField>
          )}
        </div>
        
        <Label style={{marginBottom: '12px'}}>포트폴리오</Label>
        {isEditing && 
            <div style={{border: '1px solid #ccc', borderRadius: '20px', display: 'flex', flexDirection: 'column'}}>
              <TextInput 
                type="url" 
                value={portfolioUrl} 
                onChange={(e) => setPortfolioUrl(e.target.value)}
                placeholder="포트폴리오 URL을 입력하세요"
              />
            </div>
        }
        
        {!isEditing && (
          <a href={portfolioUrl} target="_blank" rel="noopener noreferrer">
              <ActionButton color={'#adadad'}>포트폴리오 보기</ActionButton>
          </a>
        )}
    </CardContainer>
  )
}

export default AuthorProfile 