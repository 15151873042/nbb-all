import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

// 防止连续点击多次路由报错
let routerPush = Router.prototype.push;
Router.prototype.push = function push(location) {
  return routerPush.call(this, location).catch(err => err)
}

// 公共路由
const constantRoutes = [

  {// 默认页就是登录页
    path: '/',
    redirect: '/login'
  },

  {
    path: '/login',
    component: () => import('@/views/login'),
  },

  {
    path: '/index',
    component: () => import('@/views/index'),
  },

  {
    path: '/signout',
    component: () => import('@/views/signout'),
  },

  {
    path: '/404',
    component: () => import('@/views/error/404'),
  },

  {// 未能匹配路由的页面重定向到404页面
    path: '*',
    redirect: '/404',
  }
]


export default new Router({
  mode: 'history', // 去掉url中的#
  routes: constantRoutes
})
