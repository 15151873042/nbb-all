import {getRouters} from "@/api/menu";
import Layout from "@/layout";
import ParentView from '@/components/ParentView'
import InnerLink from "@/layout/components/InnerLink";
import {constantRoutes} from "@/router";

const permission = {
  state: {
    routes: [], // 用户对应的权限路由
    sidebarRouters: [],  // 侧边栏逐层嵌套菜单树
    topbarRouters: [], // 顶部导航栏路由
  },

  mutations: {

    SET_SIDEBAR_ROUTERS: (state, routes) => {
      state.sidebarRouters = routes
    }

  },

  actions: {
    // 生成路由信息
    GenerateRoutes({commit}) {
      return new Promise((resolve, reject) => {
        getRouters().then(res => {
          const sdata = JSON.parse(JSON.stringify(res.data))
          const rdata = JSON.parse(JSON.stringify(res.data))
          const sidebarRoutes = filterAsyncRouter(sdata) // 侧边栏需要逐层嵌套的路由
          const rewriteRoutes = filterAsyncRouter(rdata, false, true) // 真实的路由信息，会将多层嵌套的目录信息进行合并

          commit('SET_SIDEBAR_ROUTERS', constantRoutes.concat(sidebarRoutes))

          rewriteRoutes.push({ path: '*', redirect: '/404', hidden: true })
          resolve(rewriteRoutes)
        })
      })
    }
  }

}

function filterAsyncRouter(asyncRouterMap, lastRouter = false, type = false) {
  return asyncRouterMap.filter(route => {
    if (type && route.children) {
      route.children = filterChildren(route.children)
    }
    if (route.component) {
      // Layout ParentView 组件特殊处理
      if (route.component === 'Layout') {
        route.component = Layout
      } else if (route.component === 'ParentView') {
        route.component = ParentView
      } else if (route.component === 'InnerLink') {
        route.component = InnerLink
      } else {
        route.component = loadView(route.component)
      }
    }
    if (route.children != null && route.children && route.children.length) {
      route.children = filterAsyncRouter(route.children, route, type)
    } else {
      delete route['children']
      delete route['redirect']
    }
    return true
  })
}


function filterChildren(childrenMap, lastRouter = false) {
  var children = []
  childrenMap.forEach((el, index) => {
    if (el.children && el.children.length) {
      if (el.component === 'ParentView' && !lastRouter) {
        el.children.forEach(c => {
          c.path = el.path + '/' + c.path
          if (c.children && c.children.length) {
            children = children.concat(filterChildren(c.children, c))
            return
          }
          children.push(c)
        })
        return
      }
    }
    if (lastRouter) {
      el.path = lastRouter.path + '/' + el.path
    }
    children = children.concat(el)
  })
  return children
}

export const loadView = (view) => {
  if (process.env.NODE_ENV === 'development') {
    return (resolve) => require([`@/views/${view}`], resolve)
  } else {
    // 使用 import 实现生产环境的路由懒加载
    return () => import(`@/views/${view}`)
  }
}

export default permission
