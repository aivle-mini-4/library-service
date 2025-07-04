import React from 'react'

const Alert = ({ type = 'info', title, children, className = '', ...props }) => {
  const variants = {
    info: {
      container: 'bg-blue-50 border-blue-200 text-blue-800',
      icon: 'text-blue-400',
      title: 'text-blue-800',
      body: 'text-blue-700',
    },
    success: {
      container: 'bg-green-50 border-green-200 text-green-800',
      icon: 'text-green-400',
      title: 'text-green-800',
      body: 'text-green-700',
    },
    warning: {
      container: 'bg-yellow-50 border-yellow-200 text-yellow-800',
      icon: 'text-yellow-400',
      title: 'text-yellow-800',
      body: 'text-yellow-700',
    },
    error: {
      container: 'bg-red-50 border-red-200 text-red-800',
      icon: 'text-red-400',
      title: 'text-red-800',
      body: 'text-red-700',
    },
  }

  const icons = {
    info: (
      <svg
        className='w-5 h-5'
        fill='currentColor'
        viewBox='0 0 20 20'
      >
        <path
          fillRule='evenodd'
          d='M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z'
          clipRule='evenodd'
        />
      </svg>
    ),
    success: (
      <svg
        className='w-5 h-5'
        fill='currentColor'
        viewBox='0 0 20 20'
      >
        <path
          fillRule='evenodd'
          d='M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z'
          clipRule='evenodd'
        />
      </svg>
    ),
    warning: (
      <svg
        className='w-5 h-5'
        fill='currentColor'
        viewBox='0 0 20 20'
      >
        <path
          fillRule='evenodd'
          d='M8.257 3.099c.765-1.36 2.722-1.36 3.486 0l5.58 9.92c.75 1.334-.213 2.98-1.742 2.98H4.42c-1.53 0-2.493-1.646-1.743-2.98l5.58-9.92zM11 13a1 1 0 11-2 0 1 1 0 012 0zm-1-8a1 1 0 00-1 1v3a1 1 0 002 0V6a1 1 0 00-1-1z'
          clipRule='evenodd'
        />
      </svg>
    ),
    error: (
      <svg
        className='w-5 h-5'
        fill='currentColor'
        viewBox='0 0 20 20'
      >
        <path
          fillRule='evenodd'
          d='M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7 4a1 1 0 11-2 0 1 1 0 012 0zm-1-9a1 1 0 00-1 1v4a1 1 0 102 0V6a1 1 0 00-1-1z'
          clipRule='evenodd'
        />
      </svg>
    ),
  }

  const variant = variants[type]
  const baseClasses = 'border rounded-xl p-4 flex items-start space-x-3'
  const classes = `${baseClasses} ${variant.container} ${className}`

  return (
    <div
      className={classes}
      {...props}
    >
      <div className={`flex-shrink-0 ${variant.icon}`}>{icons[type]}</div>
      <div className='flex-1 min-w-0'>
        {title && <h3 className={`text-sm font-medium ${variant.title}`}>{title}</h3>}
        <div className={`text-sm ${variant.body}`}>{children}</div>
      </div>
    </div>
  )
}

export default Alert
