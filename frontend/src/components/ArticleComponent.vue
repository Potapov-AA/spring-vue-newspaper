<script setup>
import { showContent, hideContent } from '@/js/functions.js'
import { useTokenStore, ROLES } from '@/stores/token'
import { onMounted, ref } from 'vue'
import LikeComponent from '@/components/LikeComponent.vue'
import CommentComponent from '@/components/CommentComponent.vue'

const props = defineProps({
  article: Object
})

const date = ref(new Date(props.article.date))

function base64ToImage(base64Image, articleId) {
  let image = new Image()
  if(base64Image != null) {
    image.src = base64Image
    image.width = 200
    image.height = 200
    document.getElementById('imgElem'+articleId).appendChild(image)
  }
  
}

onMounted(() => {
  base64ToImage(props.article.image, props.article.id)
})
</script>

<template>
  <div class="d-flex pt-3 px-2 border solid w-auto" style="max-width: 1100px;">
    <div>
      <div v-if="props.article.image === null">
        <img  src="/default-news.png" width="200" height="200" />
      </div>
      <div :id="'imgElem'+props.article.id" v-else> 
      </div>
    </div>
    <div class="w-100">
      <div class="d-flex justify-space-between align-center mr-3">
        <div class="d-flex">
          <b v-for="tag in props.article.themes" :key="tag" class="mx-1">
            #<i>{{ tag }}</i>
          </b>
        </div>
        <div>
          <v-icon v-if="useTokenStore().role == ROLES.ADMIN" class="mr-2">{{ 'mdi-pen' }}</v-icon>
          <v-icon v-if="useTokenStore().role == ROLES.ADMIN" color="red">{{ 'mdi-delete' }}</v-icon>   
        </div>     
      </div>
        <h2 class="text-justify">{{ props.article.title }}</h2>      
      <div class="text-justify">
        {{ props.article.text.slice(0, 250) }}
        <span :id="'article-' + props.article.id" class="hidden">
          {{ props.article.text.slice(250) }}
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
            {{ date.getHours() }}:
            <span v-if="date.getMinutes() < 10">
              0{{ date.getMinutes() }}
            </span>
            <span v-else>
              {{ date.getMinutes() }}
            </span>
          </p>
          <LikeComponent v-bind:id="props.article.id" />
        </div>
      </div>
      <CommentComponent v-bind:id="props.article.id" />
    </div>
  </div>
</template>
