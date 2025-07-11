import React from 'react'

const TabNavigation = ({ tabs, activeTab, onTabChange }) => {
  return (
    <div className='border-b border-gray-200'>
      <nav className='-mb-px flex space-x-8 px-6'>
        {tabs.map(tab => (
          <button
            key={tab.id}
            onClick={() => onTabChange(tab.id)}
            className={`
              py-4 px-1 border-b-2 font-medium text-sm whitespace-nowrap
              ${
                activeTab === tab.id
                  ? 'border-blue-500 text-blue-600'
                  : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
              }
            `}
          >
            {tab.label}
          </button>
        ))}
      </nav>
    </div>
  )
}

export default TabNavigation
