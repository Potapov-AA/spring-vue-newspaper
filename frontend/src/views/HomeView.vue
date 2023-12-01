<script setup>
import { useArticleStore } from '@/stores/articles'
import { onMounted } from 'vue'

import HeaderComponent from '@/components/HeaderComponent.vue'

onMounted(() => {
    useArticleStore().getArticles()
})

</script>

<template>
  <v-container class="d-flex fill-height fluid justify-start align-center flex-column">
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

    <h2>sdssddsdsd</h2>
  </v-container>
</template>