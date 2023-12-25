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

export { showContent, hideContent }
