import { defineStore } from 'pinia'
import axios from 'axios'

const STORE_NAME = 'themes'

export const useThemeStore = defineStore(STORE_NAME, {
  state: () => ({
    themes: [],
    message: ''
  }),

  actions: {
    // Функция получения всех тем
    async getThemes(token) {

      await axios({
        url: '/api/allthemes',
        method: 'get',
        headers: {
          Authorization: token
        }
      })
        .then((response) => {
          {
            this.themes = response.data
            this.message = ''
          }
        })
        .catch((error) => {
          this.message = error.response.data.message
          this.themes = []
        })
    },


    // Функция проеврка статуса темы (1 - является избранной, -1 - является запретной)
    async checkStatusThemes(token, themeId) {

      let status = 0

      await axios({
        url: '/api/checkstatus/' + themeId,
        method: 'get',
        headers: {
          Authorization: token
        }
      })
        .then((response) => {
          {
            status = response.data
          }
        })
        .catch(() => {
          status = 0
        })
      
        return status
    },


    // Функция добавления понравившихся тем
    async addLikeTheme(token, themeId) {

      await axios({
        url: '/api/addliketheme/' + themeId,
        method: 'post',
        headers: {
          Authorization: token
        }
      })
    },


    // Функция добавления запретных тем
    async addDislikeTheme(token, themeId) {

      await axios({
        url: '/api/adddisliketheme/' + themeId,
        method: 'post',
        headers: {
          Authorization: token
        }
      })
    },


    // Функция удаления тем из понравившихся/запретных
    async removeLikeDislikeTheme(token, themeId) {

      await axios({
        url: '/api/deletelikedislikethemes/' + themeId,
        method: 'delete',
        headers: {
          Authorization: token
        }
      })
    },


    // Функция получения запретных тем
    async getDislikeThemes(token) {

      let dislikeThemes = []

      await axios({
        url: '/api/dislikethemes',
        method: 'get',
        headers: {
          Authorization: token
        }
      })
        .then((response) => {
          dislikeThemes = response.data
        })
        .catch(() => {
          dislikeThemes = []
        })

      return dislikeThemes
    },


    // Функция получения понравившихся тем
    async getLikeThemes(token) {

      let likeThemes = []

      await axios({
        url: '/api/likethemes',
        method: 'get',
        headers: {
          Authorization: token
        }
      })
        .then((response) => {
          likeThemes = response.data
        })
        .catch(() => {
          likeThemes = []
        })

      return likeThemes
    }
  }
})
