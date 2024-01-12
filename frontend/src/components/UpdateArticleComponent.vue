<script setup>
import { base64ToImage } from '@/js/functions.js'
import { useTokenStore, ROLES } from '@/stores/token'
import { useArticleStore } from '@/stores/articles'
import { onMounted, ref } from 'vue'

const props = defineProps({
  article: Object
})

const title = ref(props.article.title)
const themes = ref(props.article.themes.join('; '))
const text = ref(props.article.text)
const image = ref()

async function base64ToFile() {
    if(props.article.image != null) {
        const res = await fetch(props.article.image)
        const blob = await res.blob()
        const array = []
        array.push(new File([blob], 'old image', {type: blob.type}))
        image.value = array
    }
}

const message = ref('')

const showUpdateArticleDialogWindow = ref(false)

async function updateArticle(title, themesString, text, imageFile=null) {
    let themesArray = []
    if(themesString != '') {
        themesArray = themesString.split(';')
    }

    if (imageFile == null) {
        message.value = await useArticleStore().updateArticle(title, text, null, themesArray, props.article.id, useTokenStore().token)
        await useArticleStore().getArticles()
    } else {
        var reader = new FileReader()
        reader.readAsDataURL(imageFile[0])
        reader.onload = async function () {
            let imageToBase64 = reader.result
            message.value = await useArticleStore().updateArticle(title, text, imageToBase64, themesArray, props.article.id, useTokenStore().token)
            await useArticleStore().getArticles()

            if(message.value == 'Статья успешно изменена') {
                base64ToImage(props.article.image, props.article.id)
            }
        }
    }
}

onMounted(() => {
    base64ToFile() 
})
</script>

<template>
    <div>
        <v-btn icon v-if="useTokenStore().role == ROLES.ADMIN" variant="text">
            <v-icon @click="() => {
                showUpdateArticleDialogWindow = true
            }">{{ 'mdi-pen' }}</v-icon>
        </v-btn>

        <v-dialog 
            v-model="showUpdateArticleDialogWindow" 
            fullscreen
            :scrim="false"
            transition="dialog-bottom-transition"
        >
            
        <v-card>
            <v-card-title class="d-flex justify-center mt-5">
                <span class="text-h5">Редактирование статьи IDIIDIDIDDIDIDI</span>
            </v-card-title>
            <v-card-text>
                <v-container>
                    <v-text-field
                        v-model = title
                        label="Название"
                        placeholder="Человек-Паук снова актакует!"
                    />
                    <v-text-field
                        v-model = themes
                        label="Темы"
                        placeholder="Паук; DC; Marvel"
                    />
                    <v-textarea
                        v-model = text
                        label="Текст"    
                    />
                    <v-file-input
                        v-model = image
                        label="Изображение" 
                    />
                    <p>{{ message }}</p>
                </v-container>
            </v-card-text>
            <v-card-actions  class="d-flex justify-end mb-15 mr-15">
                <v-btn
                    color="red-darken-1"
                    variant="text"
                    @click="showUpdateArticleDialogWindow = false"
                >
                    Закрыть
                </v-btn>
                <v-btn
                    color="blue-darken-1"
                    variant="text"
                    id="file_input"
                    @click="() => {
                        updateArticle(title, themes, text, image)
                    }"
                >
                    Обновить
                </v-btn>
            </v-card-actions>
        </v-card>
        </v-dialog>
  </div>
</template>
