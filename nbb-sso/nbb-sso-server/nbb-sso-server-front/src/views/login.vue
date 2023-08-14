<template>
  <div class="login" v-if="showHtml">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
      <h2 class="title">SSO-SERVER 认证中心</h2>
      <el-form-item prop="username">
        <el-input
            v-model="loginForm.username"
            type="text"
            auto-complete="off"
            placeholder="账号"
            prefix-icon="el-icon-user"
        >
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
            v-model="loginForm.password"
            type="password"
            auto-complete="off"
            placeholder="密码"
            @keyup.enter.native="handleLogin"
            prefix-icon="el-icon-lock"
        >
        </el-input>
      </el-form-item>
      <el-form-item style="width:100%;">
        <el-button
            :loading="loading"
            size="medium"
            type="primary"
            style="width:100%;"
            @click.native.prevent="handleLogin"
        >
          <span v-if="!loading">登 录</span>
          <span v-else>登 录 中...</span>
        </el-button>
      </el-form-item>
    </el-form>
    <!--  底部  -->
    <div class="el-login-footer">
      <span>Copyright © 2016-2023 hupeng.vip All Rights Reserved.</span>
    </div>
  </div>
</template>

<script>

import {checkIsLoginAndCreateTicket, doLogin} from "../api/login";
import {isNotBlank} from "@/utils/ruoyi";
import {setToken} from "@/utils/auth";

export default {
  name: "Login",

  watch: {
    $route: {
      immediate: true,
      handler: function(route) {
        this.redirect = route.query && route.query.redirect;
      },
    }
  },

  data() {
    return {
      // 用户登录之后需要重定向的地址，从vue路由queryParam中获取
      redirect: undefined,
      // 用户名密码
      loginForm: {
        username: "hp",
        password: "hp",
      },
      // 当前登录页是否显示的标识
      // 为什么要有此参数，如果没有此参数，用户已登录之后，在其它系统跳转到此页面下放ticket的时候，页面会有一瞬间的显示登录页之后，然后跳转到其它系统
      showHtml: false,
      // 当前正在登录loading的标识
      loading: false,
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: "请输入您的账号" }
        ],
        password: [
          { required: true, trigger: "blur", message: "请输入您的密码" }
        ],
      },


    };
  },

  mounted() {
    this.init()
  },


  methods: {
    init() {
      let params = { redirect: this.redirect }
      checkIsLoginAndCreateTicket(params).then(res => {
        // 用户未登录，则停留在当前登录页
        if (res.code == 402) {
          this.showHtml = true
          return;
        }

        // 用户已登录，且url中有重定向地址，则做重定向跳转
        if (isNotBlank(this.redirect)) {
          location.href = res.data
          return;
        }

        // 用户已登录，且url中有没有重定向地址，则跳转到sso-server首页
        this.$router.push({path: '/index'})
      })
    },

    // 登录
    handleLogin() {
      this.loading = true
      let params = {...this.loginForm}
      doLogin(params).then(res => {
        if (res.code == 200) {
          setToken(res.data)
          // FIXME 登录成功之后是刷新当前页面
          location.reload()
        } else {
          this.$message.warning(res.msg)
        }
      }).catch(() => {
        this.loading = false
      })

    }
  }

}
</script>

<style rel="stylesheet/scss" lang="scss">
$bg:#2d3a4b;

.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-color: $bg;
  //background-image: url("../assets/images/login-background.jpg");
  background-size: cover;
}
.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #707070;
}

.login-form {
  border-radius: 6px;
  background: #ffffff;
  width: 400px;
  padding: 25px 25px 5px 25px;
  .el-input {
    height: 38px;
    input {
      height: 38px;
    }
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}
.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.login-code {
  width: 33%;
  height: 38px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}
.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}
.login-code-img {
  height: 38px;
}
</style>
