import { defineStore } from 'pinia'
import axios from 'axios'

const STORE_NAME = 'themes'

export const useThemeStore = defineStore(STORE_NAME, {
  state: () => ({
    themes: [],
    errorMessage: '' // TODO поменять имя на просто message
  }),

  actions: {
    async getThemes(token) {
      await axios({
        url: 'http://localhost:8081/api/allthemes',
        method: 'get',
        headers: {
          Authorization: token
        }
      })
        .then((response) => {
          {
            this.themes = response.data
            this.errorMessage = ''
          }
        })
        .catch((error) => {
          this.errorMessage = error.response.data.message
          this.themes = []
        })
    },

    async checkStatusThemes(token, themeId) {
      let status = 0

      await axios({
        url: 'http://localhost:8081/api/checkstatus/'+themeId,
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

    async addLikeTheme(token, themeId) {
      await axios({
        url: 'http://localhost:8081/api/addliketheme/'+themeId,
        method: 'post',
        headers: {
          Authorization: token
        }
      })
    },

    async addDislikeTheme(token, themeId) {
      await axios({
        url: 'http://localhost:8081/api/adddisliketheme/'+themeId,
        method: 'post',
        headers: {
          Authorization: token
        }
      })
    },

    async removeLikeDislikeTheme(token, themeId) {
      await axios({
        url: 'http://localhost:8081/api/deletelikedislikethemes/'+themeId,
        method: 'delete',
        headers: {
          Authorization: token
        }
      })
    },

    async getDislikeThemes(token) {
      let dislikeThemes = []

      await axios({
        url: 'http://localhost:8081/api/dislikethemes',
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

    async getLikeThemes(token) {
      let likeThemes = []

      await axios({
        url: 'http://localhost:8081/api/likethemes',
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
