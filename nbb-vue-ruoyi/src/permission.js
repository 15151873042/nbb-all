import router from '@/router'
import store from '@/store'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import {getToken} from "@/utils/auth";
import {isBlank, isNotBlank} from "@/utils/ruoyi";
import {Message} from "element-ui";
import {isRelogin} from "@/utils/request";

// 免登录地址
const whiteList = ['/login', '/auth-redirect', '/bind', '/register']

// FIXME next()执行之后方法不会结束，如果需要结束需要return
// FIXME 路由中next()有值好像就是重定向
router.beforeEach((to, from, next) => {
  NProgress.start()
  // 判断是否可以从cookie获取jwt toke
  if (isBlank(getToken())) {
    if (whiteList.includes(to.path)) {
      next() // 免登录白名单，直接进入
    } else {
      next(`/login?redirect=${to.fullPath}`) // 否则全部重定向到登录页
    }
  } else {

    // 将菜单信息放入动态标题，以便通过VueMeta动态展示title信息
    to.meta.title && store.dispatch('settings/setTitle', to.meta.title)

    if (to.path === '/login') {
      next('/') //有token且访问的是登录页，应该让其直接去首页
    } else {
      // 判断是否重新刷新了页面，重写刷新页面vuex中的数会清空，所以可以使用store里面数据是否有值判断
      if (isBlank(store.getters.name)) {
        isRelogin.show = true // FIXME 这边相当于锁，如果cookie中的token已过期，刚进入页面，调用用户用户信息接口肯定返回401，
                              // FIXME 这个时候不能弹框提示是否重新登录，锁住了request.js中401弹框提示
        // 重写刷新页面则拉去最新的用户权限信息和路由信息
        store.dispatch('GetInfo').then(() => {
          isRelogin.show = false
          store.dispatch('GenerateRoutes').then((accessRoutes) => {
            router.addRoutes(accessRoutes)
            next({...to})
          })
        }).catch((err) => { // 如果获取用户信息失败，可能是token过期了
          store.dispatch('LogOut').then(() => {
            Message.error(err)
            console.log('我执行了')
            next({ path: '/login' })
          })
        })
      } else {
        next()
      }
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})