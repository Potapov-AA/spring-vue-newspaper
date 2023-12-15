<script setup>
import axios from 'axios'
import { useTokenStore } from '@/stores/token'
import { onMounted, ref } from 'vue'

const props = defineProps({
  id: Number
})

const userLikeStatus = ref(-1)
const countLikes = ref(0)

// Добавить отправку токена
async function getLikeStatus() {
  await axios
    .get('http://localhost:8081/api/likestatus/' + props.id)
    .then((response) => {
      {
        userLikeStatus.value = response.data.userStatus
      }
    })
    .catch(() => {
      userLikeStatus.value = -1
    })
}

async function getCountLikes() {
  await axios
    .get('http://localhost:8081/api/countlikes/' + props.id)
    .then((response) => {
      {
        countLikes.value = response.data.countLikes
      }
    })
    .catch(() => {
      userLikeStatus.value = -1
    })
}

onMounted(() => {
  getCountLikes()   
})
</script>

<template>
  <div v-if="useTokenStore().logined">
    <div v-if="getLikeStatus() == 1">
      <v-icon :size="size">{{ 'mdi-heart' }}</v-icon>
    </div>
    <div v-else>
      {{ countLikes }}
      <v-icon :size="size">{{ 'mdi-heart-outline' }}</v-icon>
    </div>
  </div> 
  <div v-else>
    CANTLIKES
  </div>
</template>
