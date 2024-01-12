<script setup>
import { useTokenStore, ROLES } from '@/stores/token'
import { useArticleStore } from '@/stores/articles'
import { ref } from 'vue'

const title = ref('')
const themes = ref('')
const text = ref('')
const image = ref()

const message = ref('')

const showAddArticleDialogWindow = ref(false)

async function addArticle(title, themesString, text, imageFile=null) {
    let themesArray = []
    if(themesString != '') {
        themesArray = themesString.split(';')
    }

    if (imageFile == null) {
        message.value = await useArticleStore().addArticle(title, text, null, themesArray, useTokenStore().token)
        await useArticleStore().getArticles()
    } else {
        var reader = new FileReader()
        reader.readAsDataURL(imageFile[0])
        reader.onload = async function () {
            let imageToBase64 = reader.result
            message.value = await useArticleStore().addArticle(title, text, imageToBase64, themesArray, useTokenStore().token)
            await useArticleStore().getArticles()
        }
    }
}
</script>

<template>
    <div>
        <v-btn 
            v-if="useTokenStore().role == ROLES.ADMIN"
            color="lime-lighten-5" 
            @click="showAddArticleDialogWindow = true"
            class="mx-2"
        >
            Добавить статью
        </v-btn>

        <v-dialog 
            v-model="showAddArticleDialogWindow" 
            fullscreen
            :scrim="false"
            transition="dialog-bottom-transition"
        >
            
        <v-card>
            <v-card-title class="d-flex justify-center mt-5">
                <span class="text-h5">Добавление новой статьи</span>
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
                    @click="showAddArticleDialogWindow = false"
                >
                    Закрыть
                </v-btn>
                <v-btn
                    color="blue-darken-1"
                    variant="text"
                    @click="() => {
                        addArticle(title, themes, text, image)
                    }"
                >
                    Добавить
                </v-btn>
            </v-card-actions>
        </v-card>
        </v-dialog>
  </div>
</template>
