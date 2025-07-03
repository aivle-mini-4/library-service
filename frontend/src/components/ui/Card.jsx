import React from 'react'

const Card = ({ children, className = '', padding = 'md', shadow = 'md', glass = false, ...props }) => {
  const paddings = {
    none: '',
    sm: 'p-6',
    md: 'p-8',
    lg: 'p-10',
    xl: 'p-12',
  }

  const shadows = {
    none: '',
    sm: 'shadow-lg',
    md: 'shadow-2xl',
    lg: 'shadow-2xl shadow-purple-500/10',
    xl: 'shadow-2xl shadow-purple-500/20',
  }

  const glassClasses = glass ? 'bg-white/20 backdrop-blur-xl border-white/30' : 'bg-white border-gray-100'

  const baseClasses = `rounded-3xl border-2 ${glassClasses}`
  const classes = `${baseClasses} ${paddings[padding]} ${shadows[shadow]} ${className}`

  return (
    <div
      className={classes}
      {...props}
    >
      {children}
    </div>
  )
}

export default Card
