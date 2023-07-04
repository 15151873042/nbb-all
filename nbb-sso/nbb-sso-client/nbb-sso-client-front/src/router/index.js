import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/**
 * 路由表
 */
export const routes = [
    // 首页
    {
        name: 'home',
        path: "/home",
        component: () => import('../views/home.vue')
    },
    // SSO-登录页
    {
        name: 'login',
        path: '/login',
        component: () => import('../views/login.vue')
    },

    // 访问 / 时自动重定向到 /index
    {
        path: '/',
        redirect: '/home'
    },

    // 订单页面
    {
        name: 'order',
        path: '/order',
        component: () => import('../views/order')
    }
]

const router = new Router({
    mode: 'history', // 去掉url中的#
    routes: routes
})

export default router
