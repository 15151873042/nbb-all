<!-- 项目首页 -->
<template>
  <div>
    <h2>电商首页</h2>
    <p>当前是否登录：<b>{{isLogin}}</b></p>
    <p v-if="loginUserInfo">当前登录用户姓名：{{loginUserInfo.userInfo.userName}}</p>
    <el-button type="primary" size="medium" @click="toLoginView">你好，请登录</el-button>
    <el-button type="success" size="medium" @click="toOrderView">我的订单</el-button>
    <el-button type="warning" size="medium" @click="logout1">登出（方式1）</el-button>
    <el-button type="warning" size="medium" @click="logout2">登出（方式2）</el-button>
  </div>
</template>

<script>
import {getLoginUserInfo} from "@/api/login";
import {getToken, removeToken} from "@/utils/auth";

export default {
  name: 'App',
  data() {
    return {
      // 单点登录地址
      loginUrl: '/login?back=' + encodeURIComponent(location.href),
      // 通过调用sso-client-backend登出地址登出
      // FIXME 此处satoken生产环境是不用带的，这边添加是因为启动一个前端sso-client-front项目使用多域名测试
      ssoClientBackendLogoutUrl: process.env.VUE_APP_SSO_CLIENT_BACK_END_URL + '/sso/logout?satoken=' + getToken() + '&back=' + encodeURIComponent(location.href),
      // 通过sso-server-backend登出（引导到sso-server-front注销页面）
      ssoServerBackendLogoutUrl: process.env.VUE_APP_SSO_SERVER_FRONT_SIGNOUT_URL + '?back=' + encodeURIComponent(location.href),
      // 是否登录
      isLogin: false,
      // 当前登录用户信息
      loginUserInfo: undefined,
    }
  },
  created() {
    this.getLoginUserInfo()
  },

  methods: {

    // 获取当前登录用户信息
    getLoginUserInfo() {
      getLoginUserInfo().then(res => {
        if (res.data) {
          this.loginUserInfo = res.data
          this.isLogin = true;
        }
      })
    },

    // 跳转到登录页面
    toLoginView() {
      this.$router.push({
        name: 'login',
        query: {
          back: encodeURIComponent(location.href)
        }
      })
    },

    toOrderView() {
      this.$router.push({
        name: 'order'
      })
    },

    logout1() {
      location.href = this.ssoClientBackendLogoutUrl
      removeToken()
    },

    logout2() {

      location.href = this.ssoServerBackendLogoutUrl
      removeToken()
    }
  }
}
</script>
