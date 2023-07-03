import Cookies from 'js-cookie'

const TokenKey = process.env.VUE_APP_COOKIE_NAME

export function getToken() {
  return localStorage.getItem(TokenKey)
  // return Cookies.get(TokenKey)
}

export function setToken(token) {
  localStorage.setItem(TokenKey, token)
  // return Cookies.set(TokenKey, token)
}

export function removeToken() {
  localStorage.removeItem(TokenKey)
  // return Cookies.remove(TokenKey)
}
