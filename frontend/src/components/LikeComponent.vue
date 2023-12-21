<script setup>
import axios from 'axios'
import { useTokenStore } from '@/stores/token'
import { onMounted, ref } from 'vue'

const props = defineProps({
  id: Number
})

const userLikeStatus = ref(-1)
const countLikes = ref(0)

// Получение статуса лайка
async function getLikeStatus() {
  await axios({
    url: 'http://localhost:8081/api/likestatus/' + props.id,
    method: 'get',
    headers: {
      Authorization: useTokenStore().token
    }
  })
    .then((response) => {
      userLikeStatus.value = response.data.userStatus
    })
    .catch(() => {
      userLikeStatus.value = -1
    })
}

// Изменение статуса лайка
async function changeLikeStatus() {
  await axios({
    url: 'http://localhost:8081/api/addremovelike/' + props.id,
    method: 'post',
    headers: {
      Authorization: useTokenStore().token
    }
  })
    .then((response) => {
      {
        userLikeStatus.value = response.data.userStatus
        countLikes.value = response.data.countLike
      }
    })
    .catch(() => {
      userLikeStatus.value = -1
    })
}

// Получение количества лайков
async function getCountLikes() {
  await axios({
    url: 'http://localhost:8081/api/countlikes/' + props.id,
    method: 'get'
  })
    .then((response) => {
      {
        countLikes.value = response.data.countLike
      }
    })
    .catch(() => {
      countLikes.value = 0
    })
}

onMounted(() => {
  getCountLikes()
  if(useTokenStore().logined) {
    getLikeStatus()
  }
  window.setInterval(getCountLikes, 10000)
})
</script>

<template>
  <div v-if="useTokenStore().logined">
    <div v-if="userLikeStatus == 1">
      <button @click="changeLikeStatus()">
        <v-icon>{{ 'mdi-heart' }}</v-icon>
      </button>
      {{ countLikes }}
    </div>
    <div v-else>
      <button @click="changeLikeStatus()">
        <v-icon>{{ 'mdi-heart-outline' }}</v-icon>
      </button>
      {{ countLikes }}
    </div>
  </div>
  <div v-else>
    <v-icon>{{ 'mdi-heart-outline' }}</v-icon>
    {{ countLikes }}
  </div>
</template>
