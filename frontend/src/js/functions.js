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

export { showContent, hideContent, base64ToImage }
