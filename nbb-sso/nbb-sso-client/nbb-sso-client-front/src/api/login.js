import request from '@/utils/request'


export function isLogin() {
  return request({
    url: '/sso/isLogin',
    method: 'get',
  })
}

export function getLoginUserInfo() {
  return request({
    url: '/sso/userInfo',
    method: 'get',
  })
}

// 获取sso-server认证中心地址
export function getSsoServerLoginUrl(data) {
  return request({
    url: '/sso/getSsoServerLoginUrl',
    method: 'get',
    params: data
  })
}

export function logout() {
  return request({
    url: '/sso/logout',
    method: 'get',
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    }
  })
}


export function doLoginByTicket(data) {
  return request({
    url: '/sso/doLoginByTicket',
    method: 'get',
    params: data
  })
}


export function orderList() {
  return request({
    url: '/order/list',
    method: 'get'
  })
}
