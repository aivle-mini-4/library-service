import { useCallback, useState } from 'react'

export const useForm = (initialValues = {}, validationRules = {}) => {
  const [values, setValues] = useState(initialValues)
  const [errors, setErrors] = useState({})
  const [isLoading, setIsLoading] = useState(false)
  const [submitError, setSubmitError] = useState('')

  const handleChange = useCallback(
    e => {
      const { name, value } = e.target
      setValues(prev => {
        const newValues = {
          ...prev,
          [name]: value,
        }

        // 실시간 검증 (필드별 에러만)
        if (validationRules[name]) {
          const fieldError = validationRules[name](value, newValues)
          setErrors(prevErrors => ({
            ...prevErrors,
            [name]: fieldError,
          }))
        }

        return newValues
      })
    },
    [validationRules],
  )

  const setValue = useCallback((name, value) => {
    setValues(prev => ({
      ...prev,
      [name]: value,
    }))
  }, [])

  const validate = useCallback(() => {
    const newErrors = {}

    Object.keys(validationRules).forEach(field => {
      const error = validationRules[field](values[field], values)
      if (error) {
        newErrors[field] = error
      }
    })

    setErrors(newErrors)
    return Object.keys(newErrors).length === 0
  }, [validationRules, values])

  const reset = useCallback(() => {
    setValues(initialValues)
    setErrors({})
    setSubmitError('')
    setIsLoading(false)
  }, [initialValues])

  const handleSubmit = useCallback(
    async submitFn => {
      if (!validate()) {
        return false
      }

      setIsLoading(true)
      setSubmitError('')

      try {
        const result = await submitFn(values)
        return result
      } catch (error) {
        const errorMessage = error.response?.data?.error || error.message || '오류가 발생했습니다.'
        setSubmitError(errorMessage)
        return false
      } finally {
        setIsLoading(false)
      }
    },
    [validate, values],
  )

  return {
    values,
    errors,
    isLoading,
    submitError,
    handleChange,
    setValue,
    validate,
    reset,
    handleSubmit,
  }
}

// 자주 사용하는 검증 규칙들
export const validationRules = {
  required: value => (!value ? '필수 항목입니다.' : null),

  email: value => {
    if (!value) return null
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
    return !emailRegex.test(value) ? '올바른 이메일 형식이 아닙니다.' : null
  },

  minLength: min => value => {
    if (!value) return null
    return value.length < min ? `최소 ${min}자 이상이어야 합니다.` : null
  },

  passwordConfirm: passwordField => (value, allValues) => {
    if (!value) return null
    return value !== allValues[passwordField] ? '비밀번호가 일치하지 않습니다.' : null
  },

  requiredIf: condition => (value, allValues) => {
    if (!condition(allValues)) return null
    return !value ? '필수 항목입니다.' : null
  },
}
