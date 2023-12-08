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
        await axios
          .get('http://localhost:8081/api/articles')
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
      }
  }
})
