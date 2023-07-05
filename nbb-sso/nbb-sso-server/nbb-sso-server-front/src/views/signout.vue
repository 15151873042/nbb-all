<template>
  <div></div>
</template>

<script>

import {signout} from "../api/login";

export default {
  name: "Signout", // 单点登出页面


  watch: {
    $route: {
      immediate: true,
      handler: function(route) {
        this.back = route.query && route.query.back;
      },
    }
  },

  data() {
    return {
      back: undefined
    };
  },

  mounted() {
    this.handleSignout()
  },


  methods: {
    handleSignout() {
      signout().then(() => {
        location.href = this.back || '/login'
      })
    }
  }

}
</script>

<style rel="stylesheet/scss" lang="scss">

</style>
