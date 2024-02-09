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
async function updateListArticles() {
  if(useTokenStore().logined) {
    let lDislikeThemes = await useThemeStore().getDislikeThemes(useTokenStore().token)
    let lLikeThemes = await useThemeStore().getLikeThemes(useTokenStore().token)
    
    await useArticleStore().getArticles(lDislikeThemes, lLikeThemes)
    await useThemeStore().getThemes(useTokenStore().token)
  } else {
    let lDislikeThemes = []
    let lLikeThemes = []
    
    await useArticleStore().getArticles(lDislikeThemes, lLikeThemes)
    await useThemeStore().getThemes(useTokenStore().token)
  }
  
}

// Преобразование даты в нужный формат
function getStringDate(date) {
  let result = ''
  const newDate = new Date(date)

  let year = newDate.getFullYear().toString()

  let day
  if(newDate.getDate() < 10) {
    day = '0' + newDate.getDate().toString()
  } else {
    day = newDate.getDate().toString()
  }


  let month
  if(newDate.getMonth() < 10) {
    month = '0' + newDate.getMonth().toString()
  } else {
    month = newDate.getMonth().toString()
  }

  let hours
  if (newDate.getHours() < 10) {
    hours = '0' + newDate.getHours().toString()
  } else {
    hours = newDate.getHours().toString()
  }

  let minutes
  if (newDate.getMinutes() < 10) {
    minutes = '0' + newDate.getMinutes().toString()
  } else {
    minutes = newDate.getMinutes().toString()
  }

  result = day + '-' + month + '-' + year + ' ' + hours + ':' + minutes

  return result 
}

export { 
  showContent, 
  hideContent, 
  base64ToImage, 
  base64ToFile, 
  updateListArticles, 
  getStringDate 
}
