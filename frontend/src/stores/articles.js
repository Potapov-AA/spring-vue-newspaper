import { defineStore } from 'pinia'
import axios from 'axios'

const STORE_NAME = 'articles'

export const useArticleStore = defineStore(STORE_NAME, {
  state: () => ({
    articles: [],
    message: '',
    httpStatus: 500
  }),

  actions: {
    // Функция удаление запретных тем
    removeArticlesWithDislikeThemes(dislikeThemes) {

      let targetsIndex = []
      for(let i in this.articles) {
        for(let j in dislikeThemes) {
          if(this.articles[i].themes.find(theme => theme == dislikeThemes[j].name) != undefined) {
            targetsIndex.push(i)
            break
          }
        }
      }

      for(let i in targetsIndex.reverse()) {
        this.articles.splice(targetsIndex[i], 1)
      }
    },


    // Функция сортировки в зависимости от количества любимых тем и даты выхода статьи
    sortArticlesRelativeLikeThemes(likeThemes) {

      let lCountLikeThemes = []
      for(let i in this.articles) {
        let countThemes = 0
        for(let j in likeThemes) {
          if(this.articles[i].themes.find(theme => theme == likeThemes[j].name) != undefined) {
            countThemes++
          }
        }
        lCountLikeThemes.push({"id":i, "count":countThemes, "date": this.articles[i].date})
      }

      lCountLikeThemes.sort((a, b) => {
        if(a.count > b.count) return -1
        if(a.count < b.count) return 1

        if(new Date(a.date).getTime() > new Date(b.date).getTime()) return -1
        if(new Date(a.date).getTime() < new Date(b.date).getTime()) return 1

        return 0
      })

      let sortArticles = []
      for(let i in lCountLikeThemes){
        sortArticles.push(this.articles[lCountLikeThemes[i].id])
      }
      
      this.articles = sortArticles
    },


    // Функция формирования списка статьей
    async getArticles(dislikeThemes, likeThemes) {

      try {
        await axios({
          url: 'http://localhost:8081/api/articles', 
          method: 'get'
        })
          .then((response) => {
            {
              this.articles = response.data
              this.message = ''
              this.httpStatus = 200
            }
          })
          .catch((error) => {
            this.articles = []
            this.message = error.response.data.message
            this.httpStatus = 404
          })

          this.removeArticlesWithDislikeThemes(dislikeThemes)
          this.sortArticlesRelativeLikeThemes(likeThemes)
      } catch (error) {
        this.message = 'Идет загрузка статьей...'
        this.httpStatus = 500
      }
      
    },


    // Функция добавления статьи
    async addArticle(title, text, image, themes, token) {

      let message = ''
      let status = 400
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
          status = 200
        })
        .catch((error) => {
          message = error.response.data.message
          status = 400
        })

      return {message, status}
    },


    // Функция обновления статьи
    async updateArticle(title, text, image, themes, id, token) {

      let message = ''
      let status = 400
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
          status = 200
        })
        .catch((error) => {
          message = error.response.data.message
          status = 400
        })

        return {message, status}
    },


    // Функция удаления статьи по ID
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
