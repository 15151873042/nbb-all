<template>
  <div>
    <el-button style="margin-left:500px" @click="handlerClick" >router去详情页</el-button>
    <el-button @click="handlerClick2" >location.href去详情页</el-button>
  </div>

</template>

<script>
export default {
  name: "index",

  methods: {
    handlerClick() {
      this.$router.push('/detail')
    },

    handlerClick2() {
      location.href='/detail'
    }
  }
}
</script>

<style scoped>

</style>