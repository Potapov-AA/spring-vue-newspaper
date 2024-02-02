<script setup>
import { updateListArticles } from '@/js/functions.js'
import { useTokenStore } from '@/stores/token'
import { useArticleStore } from '@/stores/articles'
import { ref } from 'vue'

const title = ref('')
const themes = ref('')
const text = ref('')
const image = ref(null)

const message = ref('')
const status = ref()

const showAddArticleDialogWindow = ref(false)
const snackbar = ref(false)

// Функция закрывания окна добавления статьи
async function closeDialog() {
    clearField()    
    snackbar.value = true
    showAddArticleDialogWindow.value = false
    await updateListArticles()
}

// Функция очистки полей
function clearField() {
    title.value = ''
    themes.value = ''
    text.value = ''
    image.value = null

    status.value = 0
}

async function addArticle() {
    let themesArray = []
    if (themes.value != '') {
        themes.value = themes.value.replaceAll(' ', '')
        themesArray = themes.value.split(';')
    }

    if (image.value == null) {
        let result = await useArticleStore().addArticle(title.value, text.value, null, themesArray, useTokenStore().token)
        message.value = result.message
        status.value = result.status

        if(status.value == 200) {
            closeDialog()
        }        
    } else {
        let reader = new FileReader()
        reader.readAsDataURL(image.value[0])

        reader.addEventListener('load', async (e) => {
            let imageToBase64 = e.target.result

            let result  = await useArticleStore().addArticle(title.value, text.value, imageToBase64, themesArray, useTokenStore().token)
            message.value = result.message
            status.value = result.status

            if(status.value == 200) {
                closeDialog()
            }
        })
    }    
}
</script>

<template>
    <div>
        <v-btn 
            color="lime-lighten-5 standart-btn"
            @click="() => {
                message = ''
                showAddArticleDialogWindow = true
            }"
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
                    <span class="form-title">Добавление новой статьи</span>
                </v-card-title>
                <v-card-text>
                    <v-container>
                        <v-text-field 
                            v-model="title" 
                            label="Название" 
                            placeholder="Человек-Паук снова актакует!"
                        />
                        <v-text-field
                            v-model="themes" 
                            label="Темы" 
                            placeholder="Паук; DC; Marvel" 
                        />
                        <v-textarea 
                            v-model="text" 
                            label="Текст" 
                        />
                        <v-file-input v-model="image" accept="image/*" label="Изображение" />
                        <p v-if="status != 200" class="form-text">{{ message }}</p>
                    </v-container>
                </v-card-text>

                <v-card-actions class="d-flex justify-end mb-15 mr-15">
                <v-btn 
                    color="red-darken-1" 
                    variant="text"
                    @click="() => {
                        clearField()
                        showAddArticleDialogWindow = false
                    }"
                    class="form-btn"
                >
                    Закрыть
                </v-btn>
                <v-btn
                    color="blue-darken-1"
                    variant="text"
                    @click="addArticle()"
                    class="form-btn"
                >
                    Добавить
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
