import React from 'react'

function Label({ children, style }) {
  return (
    <p style={{ fontSize: '32px', marginBottom: '12px', ...style }}>
      <strong>{children}</strong>
    </p>
  )
}

export default Label 