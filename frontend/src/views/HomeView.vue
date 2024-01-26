<script setup>
import { useThemeStore } from '@/stores/themes'
import { useArticleStore } from '@/stores/articles'
import { useTokenStore } from '../stores/token'
import { onMounted, ref } from 'vue'
import HeaderComponent from '@/components/HeaderComponent.vue'
import PaginationComponent from '@/components/PaginationComponent.vue'

const isArticles = ref(false)

async function getArticles() {
  
  let themes = await useThemeStore().getDislikeThemes(useTokenStore().token)
  await useArticleStore().getArticles(themes)

  isArticles.value = useArticleStore().articles.length > 0 ? true : false
}

onMounted(async () => {
  await useTokenStore().checkTokenStatus()
  await getArticles()

  isArticles.value = useArticleStore().articles.length > 0 ? true : false

  window.setInterval(await getArticles, 10000)
  window.setInterval(await useTokenStore().checkTokenStatus(), 60000)
})
</script>

<template>
  <v-container fluid class="d-flex fill-height fluid justify-start align-center flex-column bck-img">
    <HeaderComponent />
    <div v-if="isArticles">
      <PaginationComponent v-bind:items="useArticleStore().articles" />
    </div>
    <div v-else class="py-10 px-5">
      {{ useArticleStore().errorMessage }}
    </div>
  </v-container>
</template>

<style scoped>
.bck-img{
  background-color: #F8F8FF
}
</style>