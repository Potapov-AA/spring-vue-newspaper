<script setup>
import axios from 'axios'
import { showContent, hideContent } from '@/js/functions.js'
import { useTokenStore, ROLES } from '@/stores/token'
import { onMounted, ref } from 'vue'

const props = defineProps({
  id: Number
})

const countCommentShow = ref(3)

const comments = ref([])


async function getAllComment() {
  await axios({
    url: 'http://localhost:8081/api/comments/' + props.id,
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

function getNewDate(date) {
  let result = ''
  const newDate = new Date(date)
  let day = newDate.getDate().toString()
  let month = (newDate.getMonth() + 1).toString()
  let year = newDate.getFullYear().toString()

  let hours
  if (newDate.getHours() < 10) {
    hours = '0' + newDate.getHours().toString()
  } else {
    hours = newDate.getHours().toString()
  }

  let minutes
  if (newDate.getMinutes() < 10) {
    minutes = '0' + newDate.getMinutes().toString()
  } else {
    minutes = newDate.getMinutes().toString()
  }

  result = day + '-' + month + '-' + year + ' ' + hours + ':' + minutes

  return result
}

const textComment = ref('')

async function addComment(textComment) {
  await axios({
    url: 'http://localhost:8081/api/addcomment/' + props.id,
    method: 'post',
    data: {
      text: textComment
    },
    headers: {
      Authorization: useTokenStore().token
    }
  })
  
  this.textComment = ''
  await getAllComment()
}

async function deleteComment(id) {
  await axios({
    url: 'http://localhost:8081/api/deletecomment/' + id,
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
  <!-- TODO Сделать кнопку кнопкой, сейчас при наведение курсор меняется некорректно -->
  <div 
    :id="'btn-show-comment-' + props.id"  
    @click="showContent('article-comment-' + props.id, 'btn-show-comment-' + props.id, 'btn-hide-comment-' + props.id, 'mb-3')"
    class="" 
    style="color: blue" 
  >
    Показать комментарии ({{ comments.length }})
  </div>
  <div
    :id="'btn-hide-comment-' + props.id"
    @click="() => {
      hideContent('article-comment-' + props.id, 'btn-show-comment-' + props.id, 'btn-hide-comment-' + props.id, 'mb-3') 
      countCommentShow = 3
    }"
    class="hidden"
    style="color: blue"
  >
    Скрыть комментарии
  </div>
  <div :id="'article-comment-' + props.id" class="hidden">
    <div v-if="comments.length > 0">
      <div v-for="comment in comments.slice(0, countCommentShow)" :key="comment" class="mb-3">
        <div>
          <b>{{ comment.firstName }} {{ comment.lastName }}</b> {{ getNewDate(comment.date) }}
        </div>
        <div>
          {{ comment.text }}
        </div>
        <v-btn icon v-if="useTokenStore().role == ROLES.ADMIN" variant="text">
            <v-icon color="red" @click="deleteComment(comment.id)">{{
              'mdi-delete'
            }}</v-icon>
          </v-btn>
      </div>
      <div v-if="countCommentShow < comments.length" class="mb-3">
        <p @click="countCommentShow += 3" align="center" style="color: royalblue;">
          Показать еще
        </p>
      </div>
    </div>
    <div v-else class="mb-3">
      Комментариев пока что нет, будь первым
    </div>

    <div v-if="useTokenStore().logined">
      <v-form>
        <v-textarea
          v-model="textComment"
          placeholder="Комментарий"
          variant="outlined"
          rows="1"
          row-height="15"
        />
        <div class="d-flex justify-end">
          <v-btn @click="addComment(textComment)" class="mb-3">
            Отправить
          </v-btn>
        </div>
      </v-form>
    </div>
    <div v-else class="mb-3">
      Авторизуйтесь, чтобы оставить комментарий
    </div>
  </div>
</template>
