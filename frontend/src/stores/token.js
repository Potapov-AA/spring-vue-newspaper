import { defineStore } from 'pinia'

export const ROLES = {
  USER: 'ROLE_USER',
  ANONIM: 'ANONIM',
  ADMIN: 'ROLE_ADMIN'
}

const STORE_NAME = 'token'

const getDefaultSettings = () => ({
  token: '',
  logined: false,
  role: ROLES.ANONIM
})

export const useTokenStore = defineStore(STORE_NAME, {
  state: () => ({
    token:
      localStorage.getItem('token') === null
        ? getDefaultSettings().token
        : localStorage.getItem('token'),
    logined: localStorage.getItem('token') === null ? getDefaultSettings().logined : true,
    role:
      localStorage.getItem('token') === null
        ? getDefaultSettings().role
        : this.parseJWT(localStorage.getItem('token')).roles[0]
  }),

  actions: {
    rememberToken(token) {
      this.token = 'Bearer ' + token
      this.logined = true
      this.role = this.parseJWT(token).roles[0]

      localStorage.setItem('token', this.token)
    },

    forgetToken() {
      this.token = ''
      this.logined = false
      this.role = ROLES.ANONIM

      localStorage.removeItem('token')
    },

    parseJWT(token) {
      var base64Url = token.split('.')[1]
      var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/')

      var jsonPayload = decodeURIComponent(
        atob(base64)
          .split('')
          .map(function (c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2)
          })
          .join('')
      )

      return JSON.parse(jsonPayload)
    }
  }
})
