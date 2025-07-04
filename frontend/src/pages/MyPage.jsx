import React, { useState } from 'react'
import ApiPageLayout from '../components/ApiPageLayout'
import TabNavigation from '../components/mypage/TabNavigation'
import ProfileEdit from '../components/mypage/ProfileEdit'
import ViewHistory from '../components/mypage/ViewHistory'
import Favorites from '../components/mypage/Favorites'
import Subscription from '../components/mypage/Subscription'
import Points from '../components/mypage/Points'

const tabs = [
  { id: 'profile', label: '내 정보 수정', component: ProfileEdit },
  { id: 'history', label: '열람 이력', component: ViewHistory },
  { id: 'favorites', label: '즐겨찾기', component: Favorites },
  { id: 'subscription', label: '구독 관리', component: Subscription },
  { id: 'points', label: '포인트 관리', component: Points },
]

export default function MyPage() {
  const [activeTab, setActiveTab] = useState('profile')
  const ActiveComponent = tabs.find(tab => tab.id === activeTab)?.component

  return (
    <div className='min-h-screen bg-gray-50 py-8 px-4'>
      <div className='max-w-4xl mx-auto'>
        <div className='bg-white rounded-lg shadow-sm'>
          {/* 헤더 */}
          <div className='p-6 border-b border-gray-200'>
            <h1 className='text-2xl font-bold text-gray-900'>마이페이지</h1>
            <p className='mt-1 text-sm text-gray-500'>회원 정보를 관리하고 이용 내역을 확인하세요</p>
          </div>

          {/* 탭 네비게이션 */}
          <TabNavigation
            tabs={tabs}
            activeTab={activeTab}
            onTabChange={setActiveTab}
          />

          {/* 탭 내용 */}
          <div className='p-6'>{ActiveComponent && <ActiveComponent />}</div>
        </div>
      </div>
    </div>
  )
}
