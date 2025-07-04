import React, { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'

import { manuscriptsApi } from '../api/manuscripts'
import CardContainer from '../components/author/common/CardContainer'
import Label from '../components/author/common/Label'
import TextInput from '../components/author/common/TextInput'
import TextArea from '../components/author/common/TextArea'
import ActionButton from '../components/author/common/ActionButton'
import ReadOnlyField from '../components/author/common/ReadOnlyField'

function Manuscript() {

    const navigate = useNavigate();

    const [title, setTitle] = useState('')
    const [content, setContent] = useState('')
    const [isEditing, setIsEditing] = useState(false)
    const [loading, setLoading] = useState(false)
    const { id } = useParams()

    useEffect(() => {
        if (id) {
            manuscriptsApi.getManuscript()
            .then(res => {
                const data = res.data
                setTitle(data.title)
                setContent(data.content)
            })
            .catch(err => {
                alert('원고 정보를 불러오지 못했습니다.')
            })
        }
    }, [])

    const handleEdit = () => {
        setIsEditing(!isEditing)
    }

    const handleSave = () => {
        const data = {
            'title': title,
            'content': content
        }
        if (id) {
            manuscriptsApi.updateManuscript(id, data)
            alert('저장되었습니다!')
            setIsEditing(false)
        } else {
            manuscriptsApi.createManuscript(data)
            alert('원고가 생성되었습니다!')
            navigate(-1);
        }
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
        <CardContainer>
            {isEditing && (
                <h2 style={{fontSize: '48px'}}>원고 수정</h2>
            )}
            {!id && (
                <h2 style={{fontSize: '48px'}}>새 원고</h2>
            )}
            <Label>제목</Label>
            <div style={{border: '1px solid #ccc', borderRadius: '20px', marginBottom: '32px', display: 'flex', flexDirection: 'column'}}>
                {!id || isEditing ? (
                <TextInput
                    value={title}
                    onChange={e => setTitle(e.target.value)}
                />
                ) : (
                <ReadOnlyField>{title}</ReadOnlyField>
                )}
            </div>
            <Label>내용</Label>
            <div style={{border: '1px solid #ccc', borderRadius: '20px', marginBottom: '32px', display: 'flex', flexDirection: 'column'}}>
                {!id || isEditing ? (
                <TextArea
                    value={content}
                    onChange={e => setContent(e.target.value)}
                    style={{minHeight: '200px'}}
                />
                ) : (
                <ReadOnlyField>{content}</ReadOnlyField>
                )}
            </div>
            {loading && (
                <div style={{position: 'absolute', top: 0, left: 0, width: '100%', height: '100%', background: 'rgba(255,255,255,0.7)', display: 'flex', alignItems: 'center', justifyContent: 'center', zIndex: 10}}>
                <span style={{fontSize: '32px'}}>책 출판 중입니다...</span>
                </div>
            )}
            <div style={{display: 'flex', justifyContent: 'flex-end', gap: '16px', marginTop: '40px'}}>
                {!id || isEditing ? (
                <ActionButton color="#28a745" onClick={handleSave}>저장</ActionButton>
                ) : (
                <>
                    <ActionButton color="#007bff" onClick={handleEdit}>수정</ActionButton>
                    <ActionButton color="#dc3545" onClick={handleDelete}>삭제</ActionButton>
                    <ActionButton color="#ffc107" onClick={handlePublish}>출판요청</ActionButton>
                </>
                )}
            </div>
        </CardContainer>
      </div>
    )
  }
  
  export default Manuscript