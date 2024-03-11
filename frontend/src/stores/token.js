import { defineStore } from 'pinia'
import axios from 'axios'

export const ROLES = {
  ADMIN: 'ROLE_ADMIN',
  USER: 'ROLE_USER',
  ANONIM: 'ANONIM'
}

const STORE_NAME = 'token'

const getDefaultParametrs = () => ({
  token: '',
  logined: false,
  role: ROLES.ANONIM,
  firstname: '',
  lastname: ''
})


// Функция парсинга JWT
function parseJWT(token) {

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

var fParseToken = localStorage.getItem('token') !== null ? parseJWT(localStorage.getItem('token').substring(6)) : null

export const useTokenStore = defineStore(STORE_NAME, {
  state: () => ({
    token:
      localStorage.getItem('token') === null
        ? getDefaultParametrs().token
        : localStorage.getItem('token'),
    logined: 
      localStorage.getItem('token') === null 
        ? getDefaultParametrs().logined 
        : true,
    role:
      localStorage.getItem('token') === null
        ? getDefaultParametrs().role
        : fParseToken.roles[0],
    firstname:
      localStorage.getItem('token') === null
        ? getDefaultParametrs().firstname
        : fParseToken.firstname,
    lastname:
      localStorage.getItem('token') === null
        ? getDefaultParametrs().lastname
        : fParseToken.lastname
  }),

  actions: {
    // Функция запоминания токена
    rememberToken(token) {

      this.token = 'Bearer ' + token
      this.logined = true
      this.role = parseJWT(token).roles[0]
      this.firstname = parseJWT(token).firstname
      this.lastname = parseJWT(token).lastname

      localStorage.setItem('token', this.token)
    },


    // Функция забывания токена
    forgetToken() {

      this.token = ''
      this.logined = false
      this.role = ROLES.ANONIM
      this.firstname = ''
      this.lastname = ''

      localStorage.removeItem('token')
    },


    // Функция проверки статуса токена
    async checkTokenStatus() {
      
      await axios({
        url: '/api/checktoken',
        method: 'post',
        headers: {
          Authorization: this.token
        }
      }).catch(() => {
        this.forgetToken()
      })
    }
  }
})
