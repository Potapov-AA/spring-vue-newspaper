<script setup>
import { useThemeStore } from '@/stores/themes'
import { useTokenStore } from '@/stores/token'
import { updateListArticles } from '@/js/functions.js'
import { onMounted, ref } from 'vue'


const props = defineProps({
  theme: Object
})

const status = ref(0)


// Добавление/Удаление понравившихся тем
async function addRemoveLikeTheme() {

    if(status.value == 1) {

        await useThemeStore().removeLikeDislikeTheme(useTokenStore().token, props.theme.id)
        status.value = await useThemeStore().checkStatusThemes(useTokenStore().token, props.theme.id)
        await updateListArticles()
    } else {

        await useThemeStore().addLikeTheme(useTokenStore().token, props.theme.id)
        status.value = await useThemeStore().checkStatusThemes(useTokenStore().token, props.theme.id)
        await updateListArticles()
    }
}


// Добавление/Удаление запретных тем
async function addRemoveDislikeTheme() {

    if(status.value == -1) {

        await useThemeStore().removeLikeDislikeTheme(useTokenStore().token, props.theme.id)
        status.value = await useThemeStore().checkStatusThemes(useTokenStore().token, props.theme.id)
        await updateListArticles()
    } else {

        await useThemeStore().addDislikeTheme(useTokenStore().token, props.theme.id)
        status.value = await useThemeStore().checkStatusThemes(useTokenStore().token, props.theme.id)
        await updateListArticles()
    }
}


onMounted(async () => {
  status.value = await useThemeStore().checkStatusThemes(useTokenStore().token, props.theme.id)
})
</script>

<template>
  <div class="d-flex justify-space-between align-center">
    <b class="themeName d-inline-block text-truncate">{{ props.theme.name }}</b>
    <div class="ml-4">
        <v-btn 
          v-if="status == 1" 
          @click="addRemoveLikeTheme()" 
          density="compact" 
          icon="mdi-thumb-up"
        />
        <v-btn 
          v-else 
          @click="addRemoveLikeTheme()" 
          density="compact" 
          icon="mdi-thumb-up-outline"
        />

        <v-btn 
          v-if="status == -1" 
          @click="addRemoveDislikeTheme()" 
          density="compact" 
          icon="mdi-thumb-down"
        />
        <v-btn  
          v-else 
          @click="addRemoveDislikeTheme()" 
          density="compact" 
          icon="mdi-thumb-down-outline"
        />
    </div>
  </div>
</template>

<style scoped>
.themeName {
  font-family: ThemeName;
  font-size: 20px;
  max-width: 250px;
}
</style>
