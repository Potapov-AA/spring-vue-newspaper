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
  if(base64Image != null) {
    image.src = base64Image
    image.width = 200
    image.height = 200
    document.getElementById('imgElem'+articleId).innerHTML = ''
    document.getElementById('imgElem'+articleId).appendChild(image)
  }
}


// Функция обновления списка статьей
// TODO Проверить что нормально вызывается после обновления/редактирования статьи за админа
// TODO Проверить что нормально вызывается после выбора любимых тем
// TODO ПРоверить что нормально вызывается после выбора запретных тем
// TODO Проверить что нормально вызывается после удаления из любимых тем
// TODO Проверить что нормально вызывается после удаления из запретных тем
// TODO Проверить что нормально вызывается после обновления страницы
// TODO ПРоверить что нормально вызывается после перехода на страницу
async function updateListArticles() {
  // TODO удалить после всех проверок
  console.log("Была вызвана функция обновления списка статьей")


  let lDislikeThemes = await useThemeStore().getDislikeThemes(useTokenStore().token)
  let lLikeThemes = await useThemeStore().getLikeThemes(useTokenStore().token)

  // TODO удалить после всех проверок
  console.log("Фаворитные темы: " + lLikeThemes)
  console.log("Запретные темы: " + lDislikeThemes)
  

  await useArticleStore().getArticles(lDislikeThemes, lLikeThemes)
  await useThemeStore().getThemes(useTokenStore().token)
}

export { showContent, hideContent, base64ToImage, updateListArticles }
