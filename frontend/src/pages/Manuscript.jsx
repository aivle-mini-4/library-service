import React, { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'

import { manuscriptsApi } from '../api/manuscripts'

function Manuscript() {

    const [title, setTitle] = useState('')
    const [content, setContent] = useState('')
    const [isEditing, setIsEditing] = useState(false)
    const [loading, setLoading] = useState(false)

    const { id } = useParams()

    useEffect(() => {
        manuscriptsApi.getManuscript()
            .then(res => {
                const data = res.data
                setTitle(data.title)
                setContent(data.content)
            })
            .catch(err => {
                alert('원고 정보를 불러오지 못했습니다.')
            })
    }, [])

    const handleEdit = () => {
        setIsEditing(!isEditing)
    }

    const handleSave = () => {
        const data = {
            'title': title,
            'content': content
        }
        manuscriptsApi.updateManuscript(id, data)
        alert('저장되었습니다!')
        setIsEditing(false)
    }

    const handleDelete = () => {
        if (window.confirm('정말 삭제하시겠습니까?')) {
            manuscriptsApi.deleteManuscript(id)
            alert('삭제되었습니다!')
        }
    }

    const handlePublish = async () => {
        setLoading(true)

        try {
            await manuscriptsApi.requestPublication(id)
            setLoading(false)
            alert('출판 요청이 완료되었습니다!')
            // TODO: 책 상세 화면으로 이동
        } catch (e) {
            setLoading(false)
            alert('출판 요청에 실패했습니다.')
        }
    }

    return (
      <div style={{maxWidth: '1200px', margin: '140px auto'}}>
        <div style={{border: '4px solid #ccc', borderRadius: '20px', padding: '50px', maxWidth: '1000px', margin: '0 auto', position: 'relative'}}>
            
            {isEditing && (
                <h2 style={{fontSize: '48px'}}>원고 수정</h2>
            )}

            {/* 원고 제목 */}
            <p style={{ fontSize: '32px', marginBottom: '12px' }}><strong>제목</strong></p>
            <div style={{border: '1px solid #ccc', borderRadius: '20px', marginBottom: '32px', display: 'flex', flexDirection: 'column'}}>
                {isEditing ? (
                <input
                    type="text"
                    value={title}
                    onChange={e => setTitle(e.target.value)}
                    style={{padding: '24px', fontSize: '32px', border: 'none', outline: 'none'}}
                />
                ) : (
                <p style={{padding: '24px', fontSize: '32px', margin: 0}}>{title}</p>
                )}
            </div>

            {/* 원고 내용 */}
            <p style={{ fontSize: '32px', marginBottom: '12px' }}><strong>내용</strong></p>
            <div style={{border: '1px solid #ccc', borderRadius: '20px', marginBottom: '32px', display: 'flex', flexDirection: 'column'}}>
                {isEditing ? (
                <textarea
                    value={content}
                    onChange={e => setContent(e.target.value)}
                    style={{padding: '24px', fontSize: '32px', border: 'none', outline: 'none', resize: 'vertical', minHeight: '200px'}}
                />
                ) : (
                <p style={{padding: '24px', fontSize: '32px', margin: 0}}>{content}</p>
                )}
            </div>

            {/* 로딩 표시 */}
            {loading && (
                <div style={{position: 'absolute', top: 0, left: 0, width: '100%', height: '100%', background: 'rgba(255,255,255,0.7)', display: 'flex', alignItems: 'center', justifyContent: 'center', zIndex: 10}}>
                <span style={{fontSize: '32px'}}>책 출판 작업 중입니다...</span>
                </div>
            )}

            {/* 버튼 영역 */}
            <div style={{display: 'flex', justifyContent: 'flex-end', gap: '16px', marginTop: '40px'}}>
                {isEditing ? (
                <button
                    style={{fontSize: '24px', padding: '16px 32px', backgroundColor: '#28a745', color: 'white', border: 'none', borderRadius: '10px', cursor: 'pointer'}}
                    onClick={handleSave}
                >
                    저장
                </button>
                ) : (
                <>
                    <button
                        style={{fontSize: '24px', padding: '16px 32px', backgroundColor: '#007bff', color: 'white', border: 'none', borderRadius: '10px', cursor: 'pointer'}}
                        onClick={handleEdit}
                    >
                        수정
                    </button>
                    <button
                        style={{fontSize: '24px', padding: '16px 32px', backgroundColor: '#dc3545', color: 'white', border: 'none', borderRadius: '10px', cursor: 'pointer'}}
                        onClick={handleDelete}
                    >
                        삭제
                    </button>
                    <button
                        style={{fontSize: '24px', padding: '16px 32px', backgroundColor: '#ffc107', color: 'black', border: 'none', borderRadius: '10px', cursor: 'pointer'}}
                        onClick={handlePublish} 
                    >
                        출판요청
                    </button>
                </>
                )}
            </div>
        </div>
      </div>
    )
  }
  
  export default Manuscript