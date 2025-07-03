import React, { useState, useEffect } from 'react'

import { userInfoApi } from '../api/userInfo'


function AuthorProfile() {

  const [name, setName] = useState('')
  const [email, setEmail] = useState('')
  const [basicInfo, setBasicInfo] = useState('')
  const [introduction, setIntroduction] = useState('')
  const [portfolioUrl, setPortfolioUrl] = useState('')
  const [isEditing, setIsEditing] = useState(false)
  
  useEffect(() => {
    // API로 작가 정보 받아오기
    userInfoApi.pages.writerPages.get()
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
    userInfoApi.profiles.writerProfiles.update(data)
    alert('저장되었습니다!')
    setIsEditing(false)
  }

  return (
    <div style={{border: '4px solid #ccc', borderRadius: '20px', padding: '50px', marginBottom: '52px', maxWidth: '1000px', marginRight: 'auto', marginLeft: 'auto', position: 'relative'}}>
        <button 
          style={{
            position: 'absolute',
            right: '48px',
            fontSize: '28px',
            padding: '8px 16px',
            backgroundColor: isEditing ? '#28a745' : '#007bff',
            color: 'white',
            border: 'none',
            borderRadius: '10px',
            cursor: 'pointer',
            marginTop: '32px'
          }}
          onClick={isEditing ? handleSave : handleEdit}
        >
          {isEditing ? '저장' : '수정'}
        </button>
        <h1 style={{fontSize: '52px'}}>작가 프로필</h1>
        
        {/* 이름 섹션 */}
        <h2 style={{fontSize: '48px'}}>
          {isEditing ? (
            <input 
              type="text" 
              value={name} 
              onChange={(e) => setName(e.target.value)}
              style={{fontSize: '48px', border: 'none', borderBottom: '2px solid #ccc', outline: 'none'}}
            />
          ) : (
            name
          )}
          <span style={{fontSize: '32px', color: '#888', marginLeft: '8px'}}>작가</span>
        </h2>
        <p style={{ fontSize: '24px', color: '#555', marginTop: '24px' }}>
             {email}
        </p>
        
        {/* 기본 정보 섹션 */}
        <p style={{ fontSize: '32px' }}><strong>기본 정보</strong></p>
        <div style={{border: '1px solid #ccc', borderRadius: '20px', display: 'flex', flexDirection: 'column'}}>
          {isEditing ? (
            <input 
              type="text" 
              value={basicInfo} 
              onChange={(e) => setBasicInfo(e.target.value)}
              style={{padding: '24px', marginBottom: '12px', fontSize: '32px', border: 'none', outline: 'none'}}
            />
          ) : (
            <p style={{padding: '24px' , marginBottom: '12px', fontSize: '32px'}}>{basicInfo}</p>
          )}
        </div>
        
        {/* 자기소개 섹션 */}
        <p style={{marginBottom: '12px', fontSize: '32px'}}><strong>자기소개</strong></p>
        <div style={{border: '1px solid #ccc', borderRadius: '20px', display: 'flex', flexDirection: 'column'}}>
          {isEditing ? (
            <textarea 
              value={introduction} 
              onChange={(e) => setIntroduction(e.target.value)}
              style={{padding: '24px', marginBottom: '12px', fontSize: '32px', border: 'none', outline: 'none', resize: 'vertical', minHeight: '100px'}}
            />
          ) : (
            <p style={{padding: '24px' , marginBottom: '12px', fontSize: '32px'}}>{introduction}</p>
          )}
        </div>
        
        <p style={{marginBottom: '12px', fontSize: '32px'}}><strong>포트폴리오</strong></p>
        {isEditing && 
            <div style={{border: '1px solid #ccc', borderRadius: '20px', display: 'flex', flexDirection: 'column'}}>
            {isEditing ? (
              <input 
                type="url" 
                value={portfolioUrl} 
                onChange={(e) => setPortfolioUrl(e.target.value)}
                style={{padding: '24px', marginBottom: '12px', fontSize: '32px', border: 'none', outline: 'none'}}
                placeholder="포트폴리오 URL을 입력하세요"
              />
            ) : (
              <p style={{padding: '24px' , marginBottom: '12px', fontSize: '32px'}}>{portfolioUrl}</p>
            )}
          </div>
        }
        
        {!isEditing && (
          <a href={portfolioUrl} target="_blank" rel="noopener noreferrer">
              <button style={{marginTop: '36px', fontSize: '24px', padding: '16px 32px'}}>포트폴리오 보기</button>
          </a>
        )}
    </div>
  )
}

export default AuthorProfile 