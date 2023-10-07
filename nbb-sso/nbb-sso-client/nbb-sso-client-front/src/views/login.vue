<!-- Sa-Token-SSO-Client端-登录页 -->
<template>
  <div></div>
</template>

<script>


import {doLoginByTicket, getSsoServerLoginUrl} from "@/api/login";
import {setToken} from "@/utils/auth";

export default {
  name: 'Login',

  watch: {
    $route: {
      immediate: true,
      handler: function(route) {
        this.back =  route.query && route.query.back;
        this.ticket = route.query && route.query.ticket;
      }
    }
  },


  data() {
    return {
      back: undefined,
      ticket: undefined
    }
  },
  // 页面加载后触发
  created() {
    if(this.ticket) {
      this.doLoginByTicket(this.ticket);
    } else {
      this.goSsoAuthUrl();
    }
  },
  methods: {
    // 重定向至认证中心
    goSsoAuthUrl() {
      let param = {ssoClientLoginUrl: location.href}
      getSsoServerLoginUrl(param).then(res => {
        location.href = res.data
      })
    },

    // 根据ticket值登录
    doLoginByTicket() {
      let param = {ticket: this.ticket}
      doLoginByTicket(param).then(res => {
        if (res.code == 200) {
          console.log('ticket登录成功')
          setToken(res.data)
          if (this.back) {
            location.href = decodeURIComponent(this.back);
          } else {
            this.$router.push({name: 'home'})
          }
        } else {
          this.$message.error(res.msg)
        }
      })
    }
  }

}

</script>
