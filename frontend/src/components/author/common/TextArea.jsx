import React from 'react'

function TextArea({ value, onChange, style, ...props }) {
  return (
    <textarea
      value={value}
      onChange={onChange}
      style={{
        padding: '24px',
        fontSize: '32px',
        border: 'none',
        outline: 'none',
        resize: 'vertical',
        minHeight: '100px',
        ...style
      }}
      {...props}
    />
  )
}

export default TextArea 