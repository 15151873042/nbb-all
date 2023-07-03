const getters = {
  dict: state => state.dict.dict,

  name: state => state.user.name,
  roles: state => state.user.roles,
  avatar: state => state.user.avatar, // 用户头像

  sidebar: state => state.app.sidebar, // 侧边栏状态信息
  device: state => state.app.device, // 桌面端/移动端

  sidebarRouters:state => state.permission.sidebarRouters,
  permission_routes: state => state.permission.routes,
}
export default getters
