<script setup>
import axios from 'axios'
import { useTokenStore } from '@/stores/token'
import { ref } from 'vue'
import router from '@/router/index.js'

const responseMessage = ref('')
const email = ref('')
const password = ref('')

async function login(email, password) {
  await axios({
    url: 'http://localhost:8081/api/auth',
    method: 'post',
    data: {
      email: email,
      password: password
    }
  })
    .then((response) => {
      {
        useTokenStore().rememberToken(response.data.token)
        responseMessage.value = 'Авторизация прошла успешно'
        router.push('/')
      }
    })
    .catch((error) => {
      if (error.response.status === 401) {
        responseMessage.value = 'Введены неправильные логин или пароль!'
      }
    })
}

function exit() {
  useTokenStore().forgetToken()
  responseMessage.value = ''
}
</script>

<template>
  <v-container class="d-flex fill-height fluid justify-center">
    <div v-if="!useTokenStore().logined">
      <v-sheet :elevation="9" :width="500" class="mx-auto rounded-lg pa-6">
        <v-form>
          <h2 align="center" class="mb-3">АВТОРИЗАЦИЯ</h2>
          <v-text-field
            v-model="email"
            type="email"
            label="Почта"
            placeholder="johndoe@gmail.com"
          />
          <v-text-field
            v-model="password"
            type="password"
            label="Пароль"
            placeholder="Введите пароль"
          />
          <p class="errorMessage mb-4">{{ responseMessage }}</p>
          <v-btn @click="login(email, password)" block class="mt-2"> Войти </v-btn>
          <div class="mt-4 d-flex justify-space-between">
            <router-link :to="{ name: 'home' }">Назад</router-link>
            <router-link :to="{ name: 'reg' }">Регистрация</router-link>
          </div>
        </v-form>
      </v-sheet>
    </div>
    <div v-else>
      <v-sheet :elevation="9" :width="300" class="mx-auto rounded-lg pa-6">
        <v-form>
          <h2 align="center" class="mb-3">АВТОРИЗАЦИЯ</h2>
          <p align="center" class="successMessage mb-4">{{ responseMessage }}</p>
          <v-btn @click="exit()" block class="mt-2">Выход</v-btn>
        </v-form>
      </v-sheet>
    </div>
  </v-container>
</template>
