import { useCallback, useEffect, useMemo, useRef, useState } from 'react'

/**
 * API 함수를 래핑하는 커스텀 훅
 * @param {Function} apiFunction - API 함수
 * @param {Object} options - 옵션
 * @returns {Object} 래핑된 API 함수와 상태
 */
export const useApi = (apiFunction, options = {}) => {
  const [isLoading, setIsLoading] = useState(false)
  const [error, setError] = useState(null)
  const [data, setData] = useState(null)
  const [params, setParams] = useState(options.params || [])

  const debounceTimerRef = useRef(null)

  // 컴포넌트 언마운트 시 타이머 정리
  useEffect(() => {
    return () => {
      if (debounceTimerRef.current) {
        clearTimeout(debounceTimerRef.current)
      }
    }
  }, [])

  // 옵션을 useMemo로 안정화
  const stableOptions = useMemo(
    () => ({
      timeout: 10000,
      retries: 0,
      debounce: 0, // debounce 시간 (ms)
      runOnMount: false, // 마운트 시 자동 실행 여부
      params: [], // 초기 파라미터
      ...options,
    }),
    [options.timeout, options.retries, options.debounce, options.runOnMount, options.params],
  )

  // Debounce 처리
  const debounceRequest = (callback, delay) => {
    if (debounceTimerRef.current) {
      clearTimeout(debounceTimerRef.current)
    }

    debounceTimerRef.current = setTimeout(() => {
      debounceTimerRef.current = null
      callback()
    }, delay)
  }

  // 재시도 로직
  const executeWithRetry = async (apiCall, retries) => {
    try {
      return await apiCall()
    } catch (err) {
      if (retries > 0) {
        await new Promise(resolve => setTimeout(resolve, 1000)) // 1초 대기
        return executeWithRetry(apiCall, retries - 1)
      }
      throw err
    }
  }

  // 실제 API 호출 실행
  const executeApiCall = async (apiFunc, params) => {
    setIsLoading(true)
    setError(null)

    try {
      const response = await apiFunc(...params)
      const result = response.data || response
      setData(result)
      return result
    } catch (err) {
      const errorMessage = err.response?.data?.message || err.message || 'API 호출 중 오류가 발생했습니다.'
      setError(errorMessage)
      throw err
    } finally {
      setIsLoading(false)
    }
  }

  // API 실행 함수 (설정된 파라미터 사용)
  const execute = useCallback(
    async newParams => {
      const paramsToUse = newParams || params

      // Debounce 처리
      if (stableOptions.debounce > 0) {
        return new Promise((resolve, reject) => {
          debounceRequest(async () => {
            try {
              const result = await executeWithRetry(
                () => executeApiCall(apiFunction, paramsToUse),
                stableOptions.retries,
              )
              resolve(result)
            } catch (error) {
              reject(error)
            }
          }, stableOptions.debounce)
        })
      }

      return executeWithRetry(() => executeApiCall(apiFunction, paramsToUse), stableOptions.retries)
    },
    [apiFunction, params, stableOptions],
  )

  // 마운트 시 자동 실행
  useEffect(() => {
    if (stableOptions.runOnMount) {
      execute()
    }
  }, [execute, stableOptions.runOnMount])

  // isSuccess 계산
  const isSuccess = useMemo(() => {
    if (isLoading || error) return false
    return data !== null
  }, [isLoading, error, data])

  return {
    // 상태
    isLoading,
    error,
    data,
    isSuccess,
    params,

    // 실행 함수
    execute,

    // 파라미터 관리
    setParams,
  }
}
