<script setup>
import axios from 'axios'
import { useTokenStore } from '@/stores/token'
import { onMounted, ref } from 'vue'

const props = defineProps({
  id: Number
})

const textComment = ref('')
const countCommentShow = ref(3)

function showMoreContent() {
  var btnShow = document.getElementById('btn-show-comment-' + props.id)
  var btnHide = document.getElementById('btn-hide-comment-' + props.id)
  var coomentBlock = document.getElementById('article-comment-' + props.id)

  btnHide.className = 'mb-3'
  btnShow.className = 'hidden'
  coomentBlock.className = ''
}

function hideContent() {
  var btnShow = document.getElementById('btn-show-comment-' + props.id)
  var btnHide = document.getElementById('btn-hide-comment-' + props.id)
  var coomentBlock = document.getElementById('article-comment-' + props.id)

  btnHide.className = 'hidden'
  btnShow.className = 'mb-3'
  coomentBlock.className = 'hidden'
}

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
  getAllComment()
}

onMounted(() => {
  getAllComment()
  window.setInterval(getAllComment, 10000)
})
</script>

<template>
  <div @click="showMoreContent()" :id="'btn-show-comment-' + props.id" class="" style="color: blue">
    Показать комментарии ({{ comments.length }})
  </div>
  <div
    @click="() => {
      hideContent() 
      countCommentShow = 3
    }"
    :id="'btn-hide-comment-' + props.id"
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
      </div>
      <div v-if="countCommentShow < comments.length" class="mb-3">
        <p @click="countCommentShow += 3" align="center" style="color: royalblue;">Показать еще</p>
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
        <div class="d-flex justify-end">
          <v-btn @click="addComment(textComment)" class="mb-3">Отправить</v-btn>
        </div>
      </v-form>
    </div>
    <div v-else class="mb-3">Авторизуйтесь, чтобы оставить комментарий</div>
  </div>
</template>
