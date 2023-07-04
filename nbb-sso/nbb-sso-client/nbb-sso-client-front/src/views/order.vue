<template>
  <div>
    <h2>我的订单页面</h2>
  </div>
</template>

<script>
import {orderList} from "@/api/login";

export default {
  name: "order",

  created() {
    // 获取订单列表
     orderList()
  }
}
</script>

<style scoped>

</style>