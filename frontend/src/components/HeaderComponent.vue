<script setup>
import { useTokenStore } from '@/stores/token'
import NewArticleComponent from '@/components/NewArticleComponent.vue'
import ThemeListComponent from '@/components/ThemeListComponent.vue'
</script>

<!-- TODO Перенести список тем сюда -->
<!-- TODO Выбрать более подходящий шрифт -->
<!-- TODO Сделать красивый блок пользователя, укомповать: имя, фамилию, темы, кнопку выхода -->
<template>
  <v-sheet width="100%" :elevation="4" class="px-6">
    <div class="d-flex align-center justify-space-between">
      <p class="title">The Daily Bugle</p>
      <div v-if="useTokenStore().logined" class="d-flex align-center">
        <div class="mr-4">
          {{ useTokenStore().firstname }}
          {{ useTokenStore().lastname }}
        </div>
        <ThemeListComponent v-if="useTokenStore().logined" class="mb-4"/>

        <NewArticleComponent/>
        
        <v-btn @click="useTokenStore().forgetToken()" type="button">
          Выход
        </v-btn>
      </div>
      <div v-else>
        <v-btn class="mx-2">
          <router-link :to="{ name: 'login' }" class="link">
            Войти
          </router-link>
        </v-btn>
      </div>
    </div>
  </v-sheet>
</template>

<style scoped>
.title {
  font-family: "SiteTitle";
  font-size: 100px;
}
</style>