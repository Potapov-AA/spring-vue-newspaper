<script setup>
import axios from 'axios'
import { showContent, hideContent, getStringDate } from '@/js/functions.js'
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
  <div class="d-flex flex-column align-start">
    <button
      :id="'btn-show-comment-' + props.id"
      @click="
        showContent(
          'article-comment-' + props.id,
          'btn-show-comment-' + props.id,
          'btn-hide-comment-' + props.id,
          'mb-3 text-blue'
        )
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
        <div v-for="comment in comments.slice(0, countCommentShow)" :key="comment" class="mb-3 d-flex flex-column">
          <div class="d-flex align-center">
            <b>{{ comment.firstName }} {{ comment.lastName }}</b> 

            <v-btn icon v-if="useTokenStore().role == ROLES.ADMIN" variant="text" class="ml-2">
              <v-icon color="red" @click="deleteComment(comment.id)">{{ 'mdi-delete' }}</v-icon>
            </v-btn>
          </div>
          <div>
            {{ comment.text }}
          </div>
          <div class="text-grey-darken-1" style="font-size: 14px;">
            {{ getStringDate(comment.date) }}
          </div>
        </div>
        <div v-if="countCommentShow < comments.length" class="mb-3">
          <p @click="countCommentShow += 3" align="center" style="color: royalblue">Показать еще</p>
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
            <v-btn @click="addComment(textComment)" class="mb-3"> Отправить </v-btn>
          </div>
        </v-form>
      </div>
      <div v-else class="mb-3">Авторизуйтесь, чтобы оставить комментарий</div>
    </div>
  </div>
</template>
