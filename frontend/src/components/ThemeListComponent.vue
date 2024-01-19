<script setup>
import { useThemeStore } from '@/stores/themes'
import { useTokenStore } from '@/stores/token'
import { onMounted } from 'vue'

onMounted(() => {
  useThemeStore().getThemes(useTokenStore().token)
})
</script>

<template>
  <v-expansion-panels class="h-25">
    <v-expansion-panel class="h-25">
      <v-expansion-panel-title> Темы </v-expansion-panel-title>
      <v-expansion-panel-text v-for="theme in useThemeStore().themes" :key="theme.id">
        <b >
            {{ theme.name }}
        </b>
      </v-expansion-panel-text>
    </v-expansion-panel>
  </v-expansion-panels>
</template>
