import React from 'react'

function TextInput({ value, onChange, style, ...props }) {
  return (
    <input
      type="text"
      value={value}
      onChange={onChange}
      style={{
        padding: '24px',
        fontSize: '32px',
        border: 'none',
        outline: 'none',
        ...style
      }}
      {...props}
    />
  )
}

export default TextInput 