<script setup>
import { useArticleStore } from '@/stores/articles'
import { onMounted } from 'vue'

import HeaderComponent from '@/components/HeaderComponent.vue'

onMounted(() => {
    console.log(1)
    useArticleStore().getArticles()
})

</script>

<template>
  <v-container>
    <HeaderComponent/>


    <div v-if="useArticleStore().articles.length > 0">
        <div v-for="article in useArticleStore().articles" :key="article.id">
            {{ article }}
            {{ article.date.substring(0, 10) }}
        </div>
    </div>
    <div v-else>
        {{ useArticleStore().errorMessage }}
    </div>
  </v-container>
</template>



<style></style>
