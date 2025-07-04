import React from 'react'

function ReadOnlyField({ children, style }) {
  return (
    <p style={{ padding: '24px', margin: '0', fontSize: '32px', ...style }}>
      {children}
    </p>
  )
}

export default ReadOnlyField 