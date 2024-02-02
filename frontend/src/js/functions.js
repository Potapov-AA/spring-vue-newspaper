import { useThemeStore } from '@/stores/themes'
import { useArticleStore } from '@/stores/articles'
import { useTokenStore } from '@/stores/token'


function showContent(elementId, btnShowId, btnHideId, btnClass) {
  var element = document.getElementById(elementId)
  var btnShow = document.getElementById(btnShowId)
  var btnHide = document.getElementById(btnHideId)

  element.className = ''
  btnHide.className = btnClass
  btnShow.className = 'hidden'
}

function hideContent(elementId, btnShowId, btnHideId, btnClass) {
  var element = document.getElementById(elementId)
  var btnShow = document.getElementById(btnShowId)
  var btnHide = document.getElementById(btnHideId)

  element.className = 'hidden '
  btnHide.className = 'hidden'
  btnShow.className = btnClass
}

// Функция конвертирования base64 в изображение
function base64ToImage(base64Image, articleId) {
  let image = new Image()
  image.src = base64Image
  image.width = 185
  image.height = 185
  image.style = 'border: medium solid black'
  image.className = 'mr-4'
  document.getElementById('imgElem'+articleId).innerHTML = ''
  document.getElementById('imgElem'+articleId).appendChild(image)
}

// Функция конвертирования base64 в File
async function base64ToFile(base64Image) {
  const res = await fetch(base64Image)
  const blob = await res.blob()
  const array = []
  array.push(new File([blob], 'old image', {type: blob.type}))

  return array
}


// Функция обновления списка статьей
// TODO Проверить что нормально вызывается после выбора любимых тем
// TODO ПРоверить что нормально вызывается после выбора запретных тем
// TODO Проверить что нормально вызывается после удаления из любимых тем
// TODO Проверить что нормально вызывается после удаления из запретных тем
async function updateListArticles() {
  // TODO удалить после всех проверок
  console.log("Была вызвана функция обновления списка статьей")

  if(useTokenStore().logined) {
    // TODO удалить после всех проверок
    console.log("Пользователь авторизован")

    let lDislikeThemes = await useThemeStore().getDislikeThemes(useTokenStore().token)
    let lLikeThemes = await useThemeStore().getLikeThemes(useTokenStore().token)

    // TODO удалить после всех проверок
    console.log("Фаворитные темы: " + lLikeThemes)
    console.log("Запретные темы: " + lDislikeThemes)
    
    await useArticleStore().getArticles(lDislikeThemes, lLikeThemes)
    await useThemeStore().getThemes(useTokenStore().token)
  } else {
    // TODO удалить после всех проверок
    console.log("Пользователь не авторизован")

    let lDislikeThemes = []
    let lLikeThemes = []
    
    await useArticleStore().getArticles(lDislikeThemes, lLikeThemes)
    await useThemeStore().getThemes(useTokenStore().token)
  }
  
}

export { showContent, hideContent, base64ToImage, base64ToFile, updateListArticles }
