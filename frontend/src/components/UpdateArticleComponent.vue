<script setup>
import { updateListArticles, base64ToImage, base64ToFile } from '@/js/functions.js'
import { useTokenStore, ROLES } from '@/stores/token'
import { useArticleStore } from '@/stores/articles'
import { onMounted, ref } from 'vue'

const props = defineProps({
  article: Object
})

const title = ref(props.article.title)
const themes = ref(props.article.themes.join('; '))
const text = ref(props.article.text)
const image = ref(null)

const showUpdateArticleDialogWindow = ref(false)
const snackbar = ref(false)

const message = ref('')
const status = ref()

// Функция закрывания окна добавления статьи
async function closeDialog() {
    await clearField()    
    snackbar.value = true
    showUpdateArticleDialogWindow.value = false
    await updateListArticles()
}

// Функция очистки полей
async function clearField() {
    title.value = props.article.title
    themes.value = props.article.themes.join('; ')
    text.value = props.article.text
    image.value = await base64ToFile(props.article.image)
    status.value = -1
}

async function updateArticle() {
    let themesArray = []
    if (themes.value != '') {
        themes.value = themes.value.replaceAll(' ', '')
        themesArray = themes.value.split(';')
    }

    if (image.value == null) {
        let result = await useArticleStore().updateArticle(title.value, text.value, null, themesArray, props.article.id, useTokenStore().token)
        message.value = result.message
        status.value = result.status
    } else if (image.value.length == 0) {
        let result = await useArticleStore().updateArticle(title.value, text.value, null, themesArray, props.article.id, useTokenStore().token)
        message.value = result.message
        status.value = result.status
    }
     else {
        var reader = new FileReader()
        reader.readAsDataURL(image.value[0])

        reader.addEventListener('load', async (e) => {
            let imageToBase64 = e.target.result

            let result = await useArticleStore().updateArticle(title.value, text.value, imageToBase64, themesArray, props.article.id, useTokenStore().token)

            message.value = result.message
            status.value = result.status

            if(status.value == 200) {
                await base64ToImage(imageToBase64, props.article.id)
                await closeDialog()
            }
        })
    }
}

onMounted(async () => {
    if(props.article.image != null) {
        image.value = await base64ToFile(props.article.image)
    }
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
                <span class="form-title">Редактирование статьи {{props.article.title}}</span>
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
                        accept="image/*" 
                        label="Изображение" 
                    />
                    <p v-if="status != 200 && status != -1" class="form-text">{{ message }}</p>
                </v-container>
            </v-card-text>
            <v-card-actions  class="d-flex justify-end mb-15 mr-15">
                <v-btn
                    color="red-darken-1"
                    variant="text"
                    @click="() => {
                        clearField()
                        showUpdateArticleDialogWindow = false
                    }"
                    class="form-btn"
                >
                    Закрыть
                </v-btn>
                <v-btn
                    color="blue-darken-1"
                    variant="text"
                    id="file_input"
                    @click="() => {
                        updateArticle()
                    }"
                    class="form-btn"
                >
                    Обновить
                </v-btn>
            </v-card-actions>
        </v-card>
        </v-dialog>

        <v-snackbar v-model="snackbar" :timeout="2000">
            <p class="form-text mt-2">{{ message }}</p>
            
            <template v-slot:actions>
                <v-btn 
                    color="blue" 
                    variant="text" 
                    @click="snackbar = false"
                    class="form-btn"
                > 
                    Закрыть 
                </v-btn>
            </template>
        </v-snackbar>
  </div>
</template>
