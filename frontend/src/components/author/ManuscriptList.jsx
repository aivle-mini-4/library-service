import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'

import {manuscriptsApi} from '../../api/manuscripts'


function ManuscriptList() {
  const [manuscripts, setManuscripts] = useState([])
  const navigate = useNavigate()

  const handleClick = (id) => {
    navigate(`/manuscript/${id}`)
  }

  useEffect(() => {
  manuscriptsApi.getManuscripts()
    .then(res => {
      const data = res.data
      const manuscriptsList = data.map(item => ({
        'id': item.id,
        'title': item.title,
        'updatedAt': item.updatedAt
      }))
      setManuscripts(manuscriptsList)
    })
    .catch(err => {
        alert('원고 목록을 불러오지 못했습니다.')
      }
    )
}, [])

  return (
    <div style={{border: '4px solid #ccc', borderRadius: '20px', padding: '50px', maxWidth: '1000px', margin: '0 auto'}}>
      <h1 style={{fontSize: '52px'}}>원고 목록</h1>
      <ul style={{listStyle: 'none', padding: 0}}>
        {manuscripts.map((m) => (
          <li key={m.id} 
              style={{
                marginBottom: '16px', 
                cursor: 'pointer',
                border: '1px solid #ddd',
                borderRadius: '8px',
                padding: '16px',
                backgroundColor: '#fff',
                boxShadow: '0 2px 4px rgba(0,0,0,0.1)',
                transition: 'all 0.2s ease',
                ':hover': {
                  boxShadow: '0 4px 8px rgba(0,0,0,0.15)',
                  transform: 'translateY(-2px)'
                }
              }} 
              onMouseEnter={(e) => {
                e.target.style.boxShadow = '0 4px 8px rgba(0,0,0,0.15)';
                e.target.style.transform = 'translateY(-2px)';
              }}
              onMouseLeave={(e) => {
                e.target.style.boxShadow = '0 2px 4px rgba(0,0,0,0.1)';
                e.target.style.transform = 'translateY(0)';
              }}
              onClick={() => handleClick(m.id)}>
            <strong style={{fontSize: '28px', display: 'block', marginBottom: '8px'}}>{m.title}</strong>
            <span style={{color: '#666', fontSize: '24px'}}>수정일: {m.updatedAt}</span>
          </li>
        ))}
      </ul>
    </div>
  )
}

export default ManuscriptList 