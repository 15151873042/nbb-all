<template>
  <div>
    <h2>父组件中学生姓名：{{firstName}}{{lastName}}</h2>
    <!-- v-model只能绑定value，就算绑定的是其他属性，传递到子组件中还是value属性 -->
    <Student v-model="firstName" :value2.sync="lastName"/>
  </div>
</template>

<script>
import Student from "@/components/Student";

export default {
  name: 'App',
  components: {
    Student,
  },
  data(){
    return {
      firstName: '张',
      lastName: '三',
      age: 10
    }
  }
}
</script>

<style>

</style>
