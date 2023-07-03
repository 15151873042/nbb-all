import request from '@/utils/request'


export function buildRedirectUrl(data) {
  return request({
    url: '/buildRedirectUrl',
    method: 'get',
    params: data,
  })
}


// 登录方法
export function doLogin(data) {
  return request({
    url: '/doLogin',
    method: 'post',
    data: data
  })
}

// 注册方法
export function register(data) {
  return request({
    url: '/register',
    headers: {
      isToken: false
    },
    method: 'post',
    data: data
  })
}

// 获取用户详细信息
export function getInfo() {
  return request({
    url: '/getInfo',
    method: 'get'
  })
}


// 获取验证码
export function getCodeImg() {
  return request({
    url: '/captchaImage',
    headers: {
      isToken: false
    },
    method: 'get',
    timeout: 20000
  })
}

// 首页
export function index() {
  return request({
    url: '/index',
    headers: {
      isToken: false
    },
    method: 'get',
    timeout: 20000
  })
}

export function signout(data) {
  return request({
    url: '/signout',
    method: 'get',
    params: data,
    timeout: 20000
  })
}
