import { defineStore } from 'pinia'
import axios from 'axios'

const STORE_NAME = 'articles'

export const useArticleStore = defineStore(STORE_NAME, {
  state: () => ({
    articles: [],
    errorMessage: '', // TODO поменять имя на просто message
  }),

  actions: {
    removeBadArticles(themes) {
      let targetsIndex = []
      for(let i in this.articles) {
        for(let j in themes) {
          if(this.articles[i].themes.find(theme => theme == themes[j].name) != undefined) {
            targetsIndex.push(i)
            break
          }
        }
      }

      for(let i in targetsIndex.reverse()) {
        this.articles.splice(targetsIndex[i], 1)
      }
      console.log(targetsIndex.reverse())
    },

    async getArticles(themes) {
      try {
        await axios({
          url: 'http://localhost:8081/api/articles', 
          method: 'get'
        })
          .then((response) => {
            {
              this.articles = response.data
              this.errorMessage = ''
            }
          })
          .catch((error) => {
            this.errorMessage = error.response.data.message
            this.articles = []
          })

          this.removeBadArticles(themes)
      } catch (error) {
        this.errorMessage = 'Идет загрузка статьей...'
      }
      
    },

    // TODO добавить статус для результата запроса
    async addArticle(title, text, image, themes, token) {
      let message = ''
      await axios({
        url: 'http://localhost:8081/api/newarticle', 
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
        .then((response) => {
          message = response.data.message
        })
        .catch((error) => {
          message = error.response.data.message
        })

      return message
    },

    // TODO добавить статус для результата запроса
    async updateArticle(title, text, image, themes, id, token) {
      let message = ''
      await axios({
        url: 'http://localhost:8081/api/changearticle/'+id, 
        method: 'put',
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
        .then((response) => {
          message = response.data.message
        })
        .catch((error) => {
          message = error.response.data.message
        })

      return message
    },

    async deleteArticle(id, token) {
      await axios({
        url: 'http://localhost:8081/api/deletearticle/'+id,
        method: 'delete',
        headers: {
          Authorization: token
        }
      })
    }  
  },
})
