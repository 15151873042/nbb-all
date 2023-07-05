<template>
  <div class="main">
    <h2>SSO-SERVER 认证中心 首页</h2>
    <el-button size="medium" type="primary" @click="signout">登出</el-button>
  </div>
</template>

<script>


import {index, signout} from "@/api/login";
import {removeToken} from "@/utils/auth";

export default {
  name: 'index',
  data() {
    return {
    }
  },
  created() {
    index()
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
    }
  }
}
</script>

<style scoped>
.main {
  display: flex;
  flex-direction: column;
  /*justify-content: center;*/
  align-items: center;
  height: 100%;
}
</style>
