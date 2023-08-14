<template>
  <div>
    <div class="main">
      <h2>SSO-SERVER 认证中心 首页</h2>
      <el-button size="medium" type="primary" @click="signout">登出</el-button>
    </div>
    <div class="content">
      <el-button style="width:200px; height:100px" type="warning" @click="toClient1">子系统1</el-button>
      <el-button style="width:200px; height:100px" type="warning" @click="toClient2">子系统2</el-button>
    </div>
  </div>

</template>

<script>


import {index, signout} from "@/api/login";
import {removeToken} from "@/utils/auth";

export default {
  name: 'index',

  mounted() {
    this.index()
  },

  methods: {
    index() {
      index().then(res => {
        console.log(res)
      })
    },

    // 登出
    signout() {
      signout().then(() => {
        this.$message.success("注销成功")
        removeToken() // 删除cookie
        this.$router.push({path: '/login'}); // 跳转到登录页
      })
    },

    toClient1() {
      this.$router.push({
        path: "/login",
        query: {
          redirect: 'http://sso-client.com:8000/login?back=http://sso-client.com:8000/home',
        }
      })
    },
    toClient2() {
      this.$router.push({
        path: "/login",
        query: {
          redirect: 'http://sso-client2.com:8000/login?back=http://sso-client2.com:8000/home',
        }
      })
    }
  }
}
</script>

<style scoped>
.main {
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 100%;
}

.content {
  display: flex;
  justify-content: center;
  margin-top: 50px;
}

</style>
