<script setup>
import { useTokenStore } from '@/stores/token'
</script>

<template>
  <v-sheet width="100%" :elevation="4" class="pa-6">
    <div class="d-flex align-center justify-space-between">
      <p class="title"><b>The Daily Bugle</b></p>
      <div v-if="useTokenStore().logined" class="d-flex align-center">
        <div class="mr-4">
          {{ useTokenStore().firstname }}
          {{ useTokenStore().lastname }}
        </div>
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
        <v-btn>
          <router-link :to="{ name: 'reg' }" class="link">
            Зарегестрироваться
          </router-link>
        </v-btn>
      </div>
    </div>
  </v-sheet>
</template>
