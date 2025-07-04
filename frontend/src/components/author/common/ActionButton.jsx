import React from 'react'

function ActionButton({ children, color = '#007bff', onClick, style, ...props }) {
  return (
    <button
      onClick={onClick}
      style={{
        fontSize: '24px',
        padding: '16px 32px',
        backgroundColor: color,
        color: color === '#ffc107' ? 'black' : 'white',
        border: 'none',
        borderRadius: '10px',
        cursor: 'pointer',
        ...style
      }}
      {...props}
    >
      {children}
    </button>
  )
}

export default ActionButton 