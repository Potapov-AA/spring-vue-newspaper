<script setup>
import { useTokenStore, ROLES } from '@/stores/token'
import NewArticleComponent from '@/components/NewArticleComponent.vue'
</script>

<template>
  <v-sheet width="100%" :elevation="6" class="header px-6 ">
    <div class="d-flex align-center justify-space-between">
      <p class="title">The Daily Bugle</p>

      <div @click="useTokenStore().forgetToken()" v-if="useTokenStore().logined" class="d-flex flex-column align-end">
        <div class="user">
          {{ useTokenStore().firstname }}
          {{ useTokenStore().lastname }}
          <v-btn color="red" icon="mdi-exit-to-app" variant="plain"/>
        </div>

        <NewArticleComponent v-if="useTokenStore().role == ROLES.ADMIN"/>
      </div>
      <div v-else>
        <v-btn class="enter-btn elevation-12" variant="tonal">
          <router-link :to="{ name: 'login' }" class="link">
            Войти
          </router-link>
        </v-btn>
      </div>
    </div>
  </v-sheet>
</template>

<style scoped>
.header {
  min-height: 10vh;
}

.title {
  font-family: "SiteTitle";
  font-size: 100px;
}

.user {
  font-family: "UserHeader";
  font-size: 32px;
}

.enter-btn {
  font-family: "FontButton";
  font-size: 24px;
}
</style>