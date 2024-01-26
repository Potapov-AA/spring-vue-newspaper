<script setup>
import { useThemeStore } from '@/stores/themes'
import { useTokenStore } from '@/stores/token'
import { onMounted } from 'vue'

import ThemeItemComponent from '@/components/ThemeItemComponent.vue' 

// TODO Сделать отображение новых тем после добавления статьи
// TODO Сделать отображение новых тем после обновления статьи
// TODO Сделать отображение новых тем после обновления списка статьей
onMounted(() => {
  useThemeStore().getThemes(useTokenStore().token)
})
</script>

<template>
  <v-expansion-panels class="h-25">
    <v-expansion-panel class="h-25">
      <v-expansion-panel-title> Темы </v-expansion-panel-title>
      <v-expansion-panel-text>
        <v-virtual-scroll
          :height="useThemeStore().themes.length >= 3 ? 100 : useThemeStore().themes.length * 25"
          :items="useThemeStore().themes"
        >
          <template v-slot:default="{ item }">
            <ThemeItemComponent :theme="item"/>
          </template>
        </v-virtual-scroll>
      </v-expansion-panel-text>
    </v-expansion-panel>
  </v-expansion-panels>
</template>
