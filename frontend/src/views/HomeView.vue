<script setup>
import { useArticleStore } from '@/stores/articles'
import { useTokenStore } from '@/stores/token'
import { updateListArticles } from '@/js/functions.js'
import { onMounted} from 'vue'
import HeaderComponent from '@/components/HeaderComponent.vue'
import PaginationComponent from '@/components/PaginationComponent.vue'


onMounted(async () => {
  await useTokenStore().checkTokenStatus()
  await updateListArticles()

  window.setInterval(await useTokenStore().checkTokenStatus, 600000)
  window.setInterval(await updateListArticles, 600000)
})
</script>

<template>
  <v-container fluid class="bck-img fluid fill-height d-flex flex-column align-center">
    <HeaderComponent />
    <div v-if="useArticleStore().httpStatus != 500">  
      <PaginationComponent v-bind:items="useArticleStore().articles" />
    </div>
    <div v-else class="py-10 px-5">
      <div class="spinner"></div>
    </div>
  </v-container>
</template>

<style scoped>
.bck-img{
  background-color: #F8F8FF
}
</style>