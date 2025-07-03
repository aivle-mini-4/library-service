import React from 'react'

function CardContainer({ children, style }) {
  return (
    <div style={{
      border: '4px solid #ccc',
      borderRadius: '20px',
      padding: '50px',
      maxWidth: '1000px',
      margin: '0 auto',
      position: 'relative',
      ...style
    }}>
      {children}
    </div>
  )
}

export default CardContainer 