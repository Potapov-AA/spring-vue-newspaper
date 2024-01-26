<script setup>
import { useTokenStore } from '@/stores/token'
import NewArticleComponent from '@/components/NewArticleComponent.vue'
import ThemeListComponent from '@/components/ThemeListComponent.vue'
</script>

<!-- TODO Выбрать более подходящий шрифт -->
<!-- TODO Сделать красивый блок пользователя, укомповать: имя, фамилию, темы, кнопку выхода -->
<!-- TODO Перенести темы в отдельную боковую панель -->
<template>
  <v-sheet width="100%" :elevation="4" class="px-6">
    <div class="d-flex align-center justify-space-between">
      <p class="title">The Daily Bugle</p>
      <div v-if="useTokenStore().logined" class="d-flex flex-column justify-center">
        <div class="user">
          {{ useTokenStore().firstname }}
          {{ useTokenStore().lastname }}
        </div>
        <NewArticleComponent/>
        
        <v-btn @click="useTokenStore().forgetToken()" type="button">
          Выход
        </v-btn>
        <!-- <ThemeListComponent v-if="useTokenStore().logined" class="mb-4"/> -->

        
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

.user {
  font-family: "UserHeader";
  font-size: 32px;
}
</style>