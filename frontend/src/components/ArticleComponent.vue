<script setup>
import { ref } from 'vue'

const props = defineProps({
  article: Object
})

const date = ref(new Date(props.article.date))
</script>

<template>
  <!-- <h2>{{ props.article }}</h2> -->
  <v-card class="d-flex pa-4 border solid">
    <div>
      <img v-if="props.article.image === null" src="/default-news.png" width="200" height="200" />
      <img v-else src="/default-news.png" width="200" height="200" />
    </div>
    <div>
      <div class="d-flex">
        <b v-for="tag in props.article.themes" :key="tag" class="mx-1"
          >#<i>{{ tag }}</i></b
        >
      </div>
      <h2>{{ props.article.title }}</h2>
      <div>
        <span class="d-inline-block text-truncate" style="max-width: 500px;">{{ props.article.text }}</span>
      </div>
      <p align="right">
        {{ date.getDate() }}-{{ date.getMonth() + 1 }}-{{ date.getFullYear() }}
        {{ date.getHours() }}:{{ date.getMinutes() }}
      </p>
    </div>
  </v-card>
</template>
