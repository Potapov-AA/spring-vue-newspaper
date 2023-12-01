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
  firstName: '',
  lastName: '',
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
        : this.parseJWT(localStorage.getItem('token')).roles[0],
    firstName:
      localStorage.getItem('token') === null
        ? getDefaultSettings().firstName
        : this.parseJWT(localStorage.getItem('token')).firstname,
    lastName:
      localStorage.getItem('token') === null
        ? getDefaultSettings().lastName
        : this.parseJWT(localStorage.getItem('token')).lastname
  }),

  actions: {
    rememberToken(token) {
      this.token = 'Bearer ' + token
      this.logined = true
      this.role = this.parseJWT(token).roles[0]
      this.firstName = this.parseJWT(token).firstname
      this.lastName = this.parseJWT(token).lastname

      localStorage.setItem('token', this.token)
    },

    forgetToken() {
      this.token = ''
      this.logined = false
      this.role = ROLES.ANONIM
      this.firstName = ''
      this.lastName = ''

      localStorage.removeItem('token')
    },

    parseJWT(token) {
      let base64Url = token.split('.')[1]
      let base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/')

      let jsonPayload = decodeURIComponent(
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
