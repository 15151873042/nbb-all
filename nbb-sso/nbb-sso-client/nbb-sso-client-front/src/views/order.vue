<template>
  <div v-if="showHtml">
    <h2>我的订单页面</h2>
  </div>
</template>

<script>
import {orderList} from "@/api/login";

export default {
  name: "order",

  data() {
    return {
      showHtml: false
    }
  },

  created() {
    // 获取订单列表
     orderList().then(res => {
       if (res.code == 200) {
         this.showHtml = true;
       }
     })
  }
}
</script>

<style scoped>

</style>
