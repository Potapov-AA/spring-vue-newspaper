<script setup>
import { updateListArticles } from '@/js/functions.js'
import { useThemeStore } from '@/stores/themes'
import { useTokenStore } from '@/stores/token'
import { onMounted, ref } from 'vue'

const status = ref(0)

const props = defineProps({
  theme: Object
})


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
        <v-btn @click="addRemoveLikeTheme()" v-if="status == 1" density="compact" icon="mdi-thumb-up"></v-btn>
        <v-btn @click="addRemoveLikeTheme()" v-else density="compact" icon="mdi-thumb-up-outline"></v-btn>

        <v-btn @click="addRemoveDislikeTheme()" v-if="status == -1" density="compact" icon="mdi-thumb-down"></v-btn>
        <v-btn @click="addRemoveDislikeTheme()" v-else density="compact" icon="mdi-thumb-down-outline"></v-btn> 
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
