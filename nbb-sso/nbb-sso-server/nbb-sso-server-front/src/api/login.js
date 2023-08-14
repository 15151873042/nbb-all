import request from '@/utils/request'

// 校验是否登录，登录则下放ticket
export function checkIsLoginAndCreateTicket(data) {
  return request({
    url: '/createTicket',
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

// 单点注销
export function signout(data) {
  return request({
    url: '/signout',
    method: 'get',
    params: data,
    timeout: 20000
  })
}


// 首页获取数据接口
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

