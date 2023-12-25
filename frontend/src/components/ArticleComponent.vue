<script setup>
import { ref } from 'vue'
import { showContent, hideContent } from '@/js/functions.js'
import LikeComponent from '@/components/LikeComponent.vue'
import CommentComponent from '@/components/CommentComponent.vue'

const props = defineProps({
  article: Object
})

const date = ref(new Date(props.article.date))
</script>

<template>
  <v-card class="d-flex pt-3 px-2 border solid">
    <div>
      <img v-if="props.article.image === null" src="/default-news.png" width="200" height="200" />
      <img v-else src="/default-news.png" width="200" height="200" />
    </div>
    <div>
      <div class="d-flex">
        <b v-for="tag in props.article.themes" :key="tag" class="mx-1">
          #<i>{{ tag }}</i>
        </b>
      </div>
      <h2>{{ props.article.title }}</h2>
      <div>
        {{ props.article.text.slice(0, 150) }}
        <span :id="'article-' + props.article.id" class="hidden">
          {{ props.article.text.slice(150) }}
        </span>
      </div>
      <div class="my-3 mr-2 d-flex align-center justify-space-between">
        <button
          :id="'btn-show-' + props.article.id"
          @click="showContent('article-' + props.article.id, 'btn-show-' + props.article.id, 'btn-hide-' + props.article.id, 'v-btn border pa-2')"
          class="v-btn border pa-2"
        >
          Показать больше
        </button>
        <button
          :id="'btn-hide-' + props.article.id"
          @click="hideContent('article-' + props.article.id, 'btn-show-' + props.article.id, 'btn-hide-' + props.article.id, 'v-btn border pa-2')"
          class="hidden"
        >
          Показать меньше
        </button>

        <div class="d-flex align-center">
          <p align="right" class="mr-2">
            {{ date.getDate() }}-{{ date.getMonth() + 1 }}-{{ date.getFullYear() }}
            {{ date.getHours() }}:<span v-if="date.getMinutes() < 10">0{{ date.getMinutes() }}</span
            ><span v-else>{{ date.getMinutes() }}</span>
          </p>
          <LikeComponent v-bind:id="props.article.id" />
        </div>
      </div>
      <CommentComponent v-bind:id="props.article.id" />
    </div>
  </v-card>
</template>
