<script setup>
import { useTokenStore, ROLES } from '@/stores/token'
import { useArticleStore } from '@/stores/articles'
import { showContent, hideContent, base64ToImage, getStringDate } from '@/js/functions.js'
import { onMounted, ref } from 'vue'
import LikeComponent from '@/components/LikeComponent.vue'
import CommentComponent from '@/components/CommentComponent.vue'
import UpdateArticleComponent from '@/components/UpdateArticleComponent.vue'


const props = defineProps({
  article: Object
})

const date = ref(new Date(props.article.date))

const showDeleteConfirm = ref(false)

// Функция удаления статьи
async function deleteArticle(id, token) {
  await useArticleStore().deleteArticle(id, token)
  await useArticleStore().getArticles()
}

onMounted(() => {
  base64ToImage(props.article.image, props.article.id)
})
</script>

<template>
  <div class="d-flex pt-2 px-2">
    <div :id="'imgElem' + props.article.id"></div>

    <v-card :width="1400">
      <v-card-title class="mt-1 d-flex justify-space-between align-center">
        <b class="articleTitle">{{ props.article.title }}</b>
        <div class="d-flex">
          <UpdateArticleComponent v-bind:article="props.article" />
          <v-btn
            color="red"
            icon="mdi-delete"
            v-if="useTokenStore().role == ROLES.ADMIN"
            variant="text"
            @click="
              () => {
                showDeleteConfirm = true
              }
            "
          />
          <v-dialog
            v-model="showDeleteConfirm"
            width="auto"
          >
            <v-card
              max-width="400"
            >
              <template v-slot:actions>
                <v-btn
                  class="ms-auto"
                  color="red"
                  text="Удалить"
                  @click="
                    () => {
                      deleteArticle(props.article.id, useTokenStore().token)
                      showDeleteConfirm = false
                    }
                  "
                ></v-btn>
                <v-btn
                  class="ms-auto"
                  text="Отмена"
                  @click="showDeleteConfirm = false"
                ></v-btn>
              </template>
            </v-card>
          </v-dialog>
        </div>
      </v-card-title>

      <v-card-subtitle>
        <b v-for="tag in props.article.themes" :key="tag" class="mx-1 articleSubtitle">
          #<i>{{ tag }}</i>
        </b>
      </v-card-subtitle>

      <v-card-text>
        <div v-if="article.text.length > 250" class="text-justify articleText">
          {{ props.article.text.slice(0, 250) }}
          <button
            :id="'btn-show-' + props.article.id"
            @click="
              showContent(
                'article-' + props.article.id,
                'btn-show-' + props.article.id,
                'btn-hide-' + props.article.id,
                'v-btn text-blue'
              )
            "
            class="v-btn text-blue"
          >
            Читать далее
          </button>
          <span :id="'article-' + props.article.id" class="hidden">
            {{ props.article.text.slice(250) }} </span
          ><br />
          <button
            :id="'btn-hide-' + props.article.id"
            @click="
              hideContent(
                'article-' + props.article.id,
                'btn-show-' + props.article.id,
                'btn-hide-' + props.article.id,
                'v-btn text-blue'
              )
            "
            class="hidden"
          >
            Скрыть текст
          </button>
        </div>
        <div v-else class="text-justify articleText">
          {{ props.article.text }}
        </div>
      </v-card-text>
      <v-card-actions class="mx-2 d-flex justify-space-between align-start">
        <CommentComponent v-bind:id="props.article.id" />
        <div class="d-flex align-center">
          <p align="right" class="mr-2">
            {{ getStringDate(date) }}
          </p>
          <LikeComponent v-bind:id="props.article.id" />
        </div>
      </v-card-actions>
    </v-card>
  </div>
</template>

<style scoped>
.articleTitle {
  font-family: ArticleTitle;
  font-size: 28px;
}

.articleSubtitle {
  font-family: ThemeName;
  font-size: 16px;
}

.articleText {
  font-family: ArticleText;
  font-size: 16px;
}
</style>
