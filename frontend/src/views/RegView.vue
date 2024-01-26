<script setup>
import axios from 'axios'
import { ref } from 'vue'

const responseMessage = ref('')
const isSuccess = ref(false)

const firstname = ref('')
const lastname = ref('')
const email = ref('')
const password = ref('')
const confirmPassword = ref('')

async function registration(firstname, lastname, email, password, confPassword) {
  await axios({
    url: 'http://localhost:8081/api/registration',
    method: 'post',
    data: {
      firstname: firstname,
      lastname: lastname,
      email: email,
      password: password,
      confirmPassword: confPassword
    }
  })
    .then((response) => {
      {
        responseMessage.value = response.data.message
        isSuccess.value = true
      }
    })
    .catch((error) => {
      responseMessage.value = error.response.data.message
      isSuccess.value = false
    })
}
</script>

<template>
  <v-container class="d-flex fill-height fluid justify-center">
    <v-sheet :elevation="9" :width="500" class="mx-auto rounded-lg pa-6">
      <v-form>
        <h2 align="center" class="mb-3">РЕГИСТРАЦИЯ</h2>

        <v-text-field v-model="firstname" type="text" label="Имя" placeholder="Иван" />
        <v-text-field 
          v-model="lastname" 
          type="text" 
          label="Фамилия" 
          placeholder="Иванов" />
        <v-text-field 
          v-model="email" 
          type="email" 
          label="Почта" 
          placeholder="johndoe@gmail.com" />
        <v-text-field
          v-model="password"
          type="password"
          label="Пароль"
          placeholder="Введите пароль"
        />
        <v-text-field
          v-model="confirmPassword"
          type="password"
          label="Подтвердите пароль"
          placeholder="Подтвердите пароль"
        />

        <p align="center" :class="{ errorMessage: !isSuccess, successMessage: isSuccess, 'mb-4': true }">
          {{ responseMessage }}
        </p>

        <v-btn
          @click="registration(firstname, lastname, email, password, confirmPassword)"
          block
          class="mt-2"
        >
          Регистрация
        </v-btn>

        <div class="mt-4 d-flex justify-space-between">
          <router-link :to="{ name: 'login' }">Назад</router-link>
          <router-link :to="{ name: 'home' }">На главную</router-link>
        </div>
      </v-form>
    </v-sheet>
  </v-container>
</template>
