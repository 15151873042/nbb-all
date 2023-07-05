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
          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
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
        <div style="float: right;" v-if="register">
          <router-link class="link-type" :to="'/register'">立即注册</router-link>
        </div>
      </el-form-item>
    </el-form>
    <!--  底部  -->
    <div class="el-login-footer">
      <span>Copyright © 2016-2023 hupeng.vip All Rights Reserved.</span>
    </div>
  </div>
</template>

<script>

import {createTicket, doLogin} from "../api/login";
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
      showHtml: false,
      codeUrl: "",
      loginForm: {
        username: "hp",
        password: "hp",
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: "请输入您的账号" }
        ],
        password: [
          { required: true, trigger: "blur", message: "请输入您的密码" }
        ],
      },
      loading: false,
      // 验证码开关
      captchaEnabled: true,
      // 注册开关
      register: false,
      redirect: undefined
    };
  },

  mounted() {
    this.init()
  },


  methods: {
    init() {
      let params = { redirect: this.redirect }
      createTicket(params).then(res => {
        // 用户未登录，则停留在当前登录页
        if (res.code != 200) {
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
