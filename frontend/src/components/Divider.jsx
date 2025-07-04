import React from 'react'

const Divider = ({ className = '', ...props }) => {
  const baseClasses = 'border-t border-gray-200'
  const classes = `${baseClasses} ${className}`

  return (
    <hr
      className={classes}
      {...props}
    />
  )
}

export default Divider
