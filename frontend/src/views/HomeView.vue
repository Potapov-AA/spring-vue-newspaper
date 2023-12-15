<script setup>
import { useArticleStore } from '@/stores/articles'
import { onMounted, ref } from 'vue'

import HeaderComponent from '@/components/HeaderComponent.vue'

import PaginationComponent from '@/components/PaginationComponent.vue'

const isArticles = ref(false)

function updateArticle() {
  useArticleStore().getArticles()
  isArticles.value = useArticleStore().articles.length > 0 ? true : false
}

onMounted(async() => {
  await useArticleStore().getArticles()
  isArticles.value = useArticleStore().articles.length > 0 ? true : false
  window.setInterval(updateArticle, 30000)
})
</script>

<template>
  <v-container class="d-flex fill-height fluid justify-start align-center flex-column">
    <HeaderComponent/>
    <div v-if="isArticles">      
      <PaginationComponent v-bind:items="useArticleStore().articles"/>
    </div>
    <div v-else class="py-10 px-5">
      {{ useArticleStore().errorMessage }}
    </div>
  </v-container>
</template>