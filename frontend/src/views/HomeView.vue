<script setup>
import axios from 'axios'
import { useArticleStore } from '@/stores/articles'
import { useTokenStore } from '../stores/token'
import { onMounted, ref } from 'vue'
import HeaderComponent from '@/components/HeaderComponent.vue'
import PaginationComponent from '@/components/PaginationComponent.vue'

async function checkToken() {
  await axios({
    url: 'http://localhost:8081/api/checktoken',
    method: 'post',
    headers: {
      Authorization: useTokenStore().token
    }
  }).catch(() => {
    useTokenStore().forgetToken()
  })
}

const isArticles = ref(false)

function updateArticle() {
  useArticleStore().getArticles()
  isArticles.value = useArticleStore().articles.length > 0 ? true : false
}

onMounted(async () => {
  checkToken()
  await useArticleStore().getArticles()

  isArticles.value = useArticleStore().articles.length > 0 ? true : false

  window.setInterval(updateArticle, 60000)
  window.setInterval(checkToken, 60000)
})
</script>

<template>
  <v-container class="d-flex fill-height fluid justify-start align-center flex-column">
    <HeaderComponent />
    <div v-if="isArticles">
      <PaginationComponent v-bind:items="useArticleStore().articles" />
    </div>
    <div v-else class="py-10 px-5">
      {{ useArticleStore().errorMessage }}
    </div>
  </v-container>
</template>
