import { defineStore } from 'pinia'
import axios from 'axios'

const STORE_NAME = 'articles'

export const useArticleStore = defineStore(STORE_NAME, {
  state: () => ({
    articles: [],
    errorMessage: ''
  }),

  actions: {
    async getArticles() {
      try {
        await axios({
          url: 'http://localhost:8081/api/articles',
          method: 'get'
        })
          .then((response) => {
            {
              this.articles = response.data.reverse()
              this.errorMessage = ''
            }
          })
          .catch((error) => {
            this.errorMessage = error.response.data.message
            this.articles = []
          })
      } catch (error) {
        this.errorMessage = 'Идет загрузка статьей...'
      }
    },

    async addArticles(title, text, image, themes, token) {
      await axios({
        url: '127.0.0.1:8081/api/newarticle',
        method: 'post',
        data: {
          title: title,
          text: text,
          image: image,
          themes: themes
        },
        headers: {
          Authorization: token
        }
      })
    }
  }
})
