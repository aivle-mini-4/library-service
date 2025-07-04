import { createSlice } from '@reduxjs/toolkit'

const initialState = {
  token: localStorage.getItem('token'),
  userId: localStorage.getItem('userId'),
  isAuthenticated: !!localStorage.getItem('token'),
}

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    login: (state, action) => {
      const { token, userId } = action.payload
      state.token = token
      state.userId = userId
      state.isAuthenticated = true
      localStorage.setItem('token', token)
      localStorage.setItem('userId', userId)
    },
    logout: state => {
      state.token = null
      state.userId = null
      state.isAuthenticated = false
      localStorage.removeItem('token')
      localStorage.removeItem('userId')
    },
  },
})

export const { login, logout } = authSlice.actions
export default authSlice.reducer
