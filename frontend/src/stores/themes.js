import { defineStore } from 'pinia'
import axios from 'axios'

const STORE_NAME = 'themes'

export const useThemeStore = defineStore(STORE_NAME, {
  state: () => ({
    themes: [],
    errorMessage: '', // TODO поменять имя на просто message
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
  },
})
