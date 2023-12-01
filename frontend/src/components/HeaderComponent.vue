<script setup>
import { useTokenStore } from '@/stores/token'
</script>

<template>
  <div>
    <p class="title"><b>The Daily Bugle</b></p>

    <div v-if="useTokenStore().logined">
      <v-btn type="button">Выход</v-btn>
    </div>
    <div v-else>
        <router-link :to="{ name: 'login' }">Войти</router-link>
        <router-link :to="{ name: 'reg' }">Зарегестрироваться</router-link>
    </div>
  </div>
</template>


<style></style>
