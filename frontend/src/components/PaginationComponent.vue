<script setup>
import { useTokenStore, ROLES } from '@/stores/token'
import ArticleComponent from '@/components/ArticleComponent.vue'
import ThemeListComponent from '@/components/ThemeListComponent.vue'
import { computed, ref } from 'vue'

const props = defineProps({
  items: Array
})

const pageNumber = ref(0)
const countItemsOnPage = ref(3)

function nextPage() {
  pageNumber.value++
}

function prevPage() {
  pageNumber.value--
}

const pageCount = computed(() => {
  let itemsCount = props.items.length
  return Math.ceil(itemsCount / countItemsOnPage.value)
})

const paginatedData = computed(() => {
  let start = pageNumber.value * countItemsOnPage.value
  let end = start + countItemsOnPage.value

  return props.items.slice(start, end)
})
</script>

<template>
  <div class="py-5 px-5">
    <div class="d-flex justify-space-left align-top">
      <ThemeListComponent v-if="useTokenStore().role == ROLES.USER" class="mb-4"/>
      <v-btn class="mb-4 mx-4">Скрыть не понравившиеся темы</v-btn>
      <v-btn class="mb-4 mx-4">Только избранные темы</v-btn>
      <div class="d-flex  justify-center mb-4">
        <v-btn @click="prevPage" :disabled="pageNumber === 0">
          Назад
        </v-btn>
        <p class="mx-5 mt-1 text-h5">{{ pageNumber + 1 }}</p>
        <v-btn @click="nextPage" :disabled="pageNumber === pageCount-1">
          Далее
        </v-btn>
      </div>
    </div>
    <div v-for="item in paginatedData" :key="item.id" class="mb-4">
      <ArticleComponent v-bind:article="item" />
    </div>
  </div>
</template>
