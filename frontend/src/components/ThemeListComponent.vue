<script setup>
import { useThemeStore } from '@/stores/themes'
import { ref } from 'vue'
import ThemeItemComponent from '@/components/ThemeItemComponent.vue'


const themeDialog = ref(false)
</script>

<template>
  <div>
    <v-btn 
      color="lime-lighten-5" 
      @click="themeDialog = true" 
      class="standart-btn"
    > 
      Темы 
    </v-btn>

    <v-dialog v-model="themeDialog" width="auto">
      <v-card :width="400">
        <v-card-title class="d-flex justify-space-between align-center mt-4"> 
          <p class="themeListTitle">Темы</p>
           
          <v-btn 
            @click="themeDialog = false"
            color="red" 
            icon="mdi-close" 
            variant="plain"
          />

        </v-card-title>
        
        <v-card-text>
          <v-virtual-scroll :height="400" :items="useThemeStore().themes">
            <template v-slot:default="{ item }">
              <ThemeItemComponent :theme="item" />
            </template>
          </v-virtual-scroll>
        </v-card-text>
      </v-card>
    </v-dialog>
  </div>
</template>

<style scoped>
.themeListTitle {
  font-family: LoginTitle;
  font-size: 48px;
}
</style>
