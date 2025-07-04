import React from 'react'

const Textarea = ({ label, error, className = '', size = 'md', ...props }) => {
  const sizes = {
    sm: 'px-3 py-1.5 text-sm',
    md: 'px-4 py-2.5 text-sm',
    lg: 'px-4 py-3 text-base',
  }

  const baseClasses =
    'block w-full border rounded-xl transition-all duration-200 focus:outline-none focus:ring-2 focus:ring-offset-0 placeholder:text-gray-400 resize-none'
  const errorClasses = error
    ? 'border-red-300 focus:border-red-500 focus:ring-red-200'
    : 'border-gray-200 focus:border-blue-500 focus:ring-blue-200'
  const classes = `${baseClasses} ${sizes[size]} ${errorClasses} ${className}`

  return (
    <div className='space-y-1'>
      {label && <label className='block text-sm font-medium text-gray-700'>{label}</label>}
      <textarea
        className={classes}
        {...props}
      />
      {error && (
        <p className='text-sm text-red-600 flex items-center'>
          <svg
            className='w-4 h-4 mr-1'
            fill='currentColor'
            viewBox='0 0 20 20'
          >
            <path
              fillRule='evenodd'
              d='M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7 4a1 1 0 11-2 0 1 1 0 012 0zm-1-9a1 1 0 00-1 1v4a1 1 0 102 0V6a1 1 0 00-1-1z'
              clipRule='evenodd'
            />
          </svg>
          {error}
        </p>
      )}
    </div>
  )
}

export default Textarea
