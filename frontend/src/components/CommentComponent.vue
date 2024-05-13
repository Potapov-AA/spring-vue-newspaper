<script setup>
import { useTokenStore, ROLES } from '@/stores/token'
import { showContent, hideContent, getStringDate } from '@/js/functions.js'
import axios from 'axios'
import { onMounted, ref } from 'vue'


const props = defineProps({
  id: Number
})

const countCommentShow = ref(3)

const comments = ref([])


// Функция получения всех комментариев к статье
async function getAllComment() {

  await axios({
    url: '/api/comments/' + props.id,
    method: 'get'
  })
    .then((response) => {
      {
        comments.value = response.data.reverse()
      }
    })
    .catch(() => {
      comments.value = []
    })
}


const textComment = ref('')
const warringMessage = ref('')

// Функция добавления комментариев
async function addComment(textComment) {
  
  warringMessage.value = ''

  if (textComment.trim() == "") {
    warringMessage.value = "Комментарий не может быть пустым"
    return
  }

  await axios({
    url: '/api/addcomment/' + props.id,
    method: 'post',
    data: {
      text: textComment
    },
    headers: {
      Authorization: useTokenStore().token
    }
  })
    .catch((error) => {          
            if (error.response.status == 500) {
              console.log(error.response.status)
              warringMessage.value = "Комментария должен содержать меньше 1000 символов!"
            }
    })
  
  this.textComment = ''


  await getAllComment()
}


// Функция удаления комментариев
async function deleteComment(id) {

  await axios({
    url: '/api/deletecomment/' + id,
    method: 'delete',
    headers: {
      Authorization: useTokenStore().token
    }
  })

  await getAllComment()
}


onMounted(async () => {
  await getAllComment()
  window.setInterval(getAllComment, 10000)
})
</script>

<template>
  <div class="d-flex flex-column align-start">
    <button
      :id="'btn-show-comment-' + props.id"
      @click="()=>{
        warringMessage = ''
        showContent(
          'article-comment-' + props.id,
          'btn-show-comment-' + props.id,
          'btn-hide-comment-' + props.id,
          'mb-3 text-blue'
        )
      }
      "
      class="text-blue"
    >
      Показать комментарии ({{ comments.length }})
    </button>
    <button
      :id="'btn-hide-comment-' + props.id"
      @click="
        () => {
          hideContent(
            'article-comment-' + props.id,
            'btn-show-comment-' + props.id,
            'btn-hide-comment-' + props.id,
            'mb-3 text-blue'
          )
          countCommentShow = 3
        }
      "
      class="hidden"
    >
      Скрыть комментарии
    </button>
    <div :id="'article-comment-' + props.id" class="hidden">
      <div v-if="comments.length > 0">
        <div 
          v-for="comment in comments.slice(0, countCommentShow)" 
          :key="comment" 
          class="mb-3 d-flex flex-column"
        >
          <v-card :width="500">
            <v-card-title>
              <div class="d-flex align-center">
                <b>{{ comment.firstName }} {{ comment.lastName }}</b> 

                <v-btn 
                  icon 
                  v-if="useTokenStore().role == ROLES.ADMIN" 
                  variant="text" 
                  class="ml-2"
                >
                  <v-icon color="red" @click="deleteComment(comment.id)">{{ 'mdi-delete' }}</v-icon>
                </v-btn>
              </div>
            </v-card-title>
            <v-card-text class="text-body-1">
              {{ comment.text }}
            </v-card-text>
            <v-card-actions class="mx-2 d-flex justify-space-between align-start">
              <div class="text-grey-darken-1" style="font-size: 14px;">
                {{ getStringDate(comment.date) }}
              </div>
            </v-card-actions>
          </v-card>
        </div>
        <div v-if="countCommentShow < comments.length" class="mb-3">
          <p 
            @click="countCommentShow += 3" 
            align="center" 
            style="color: royalblue"
          >
            Показать еще
          </p>
        </div>
      </div>
      <div v-else class="mb-3">Комментариев пока что нет, будь первым</div>

      <div v-if="useTokenStore().logined">
        <v-form>
          <v-textarea
            v-model="textComment"
            placeholder="Комментарий"
            variant="outlined"
            rows="1"
            row-height="15"
          />
          <div class="warringMessage text-center">{{ warringMessage }}</div>
          <div class="d-flex justify-end">
            <v-btn @click="addComment(textComment)" class="mb-3"> Отправить </v-btn>
          </div>
        </v-form>
      </div>
      <div v-else class="mb-3">Авторизуйтесь, чтобы оставить комментарий</div>
    </div>
  </div>
</template>

<style scoped>
.warringMessage {
  color: red;
}
</style>
