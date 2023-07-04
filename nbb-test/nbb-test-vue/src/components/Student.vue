<template>
  <div>
    <h2>子组件中学生姓名：{{value}}{{value2}}</h2>
    <button @click="handleEditSync">子组件中修改父组件中的值</button>
    <button @click="handleEdit">子组件中修改子组件中props的值</button>
  </div>
</template>

<script>

export default {
  name: "Student",
  props: ['value', 'value2'],
  methods: {
    handleEdit() {
      this.value = '李'
      this.value2 = '四'
    },

    handleEditSync() {
      this.$emit('input', '李') // 通过改变父组件的值从而达到修改子组件的值
      this.$emit('update:value2', '四') // 通过改变父组件的值从而达到修改子组件的值
    }
  }
}
</script>

<style scoped>

</style>