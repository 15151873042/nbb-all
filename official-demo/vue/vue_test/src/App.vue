<template>
  <div>
    <p>姓名：{{userInfo.name}}</p>
    <p>性别：{{userInfo.sex}}</p>
    <p>年龄：{{userInfo.age}}</p>
    <p>爱好：{{userInfo.hobby}}</p>
    <button @click="edit">修改</button>
  </div>

</template>

<script>

	export default {
		name:'App',

    data() {
      return {
        userInfo: {
          name: '张三',
          age: 10,
          hobby: ['1', '2']
        },
      }
    },

    methods: {
      edit() {

        // 以下3个操作均不触发响应式，但是只要有一个触发响应式的操作一起执行，以下3个操作就能触发响应式
        this.userInfo.sex = '男' // 添加了一个属性
        delete this.userInfo.name // 删除了一个属性
        this.userInfo.hobby[0] = 'a' // 直接修改数组下标元素

        // 添加一个额外操作触发响应式
        this.userInfo.age = 20

        /// 直接修改对象引用也触发响应式
        // this.userInfo = {
        //   name: '李四',
        //   age: 20,
        //   sex: '女',
        //   hobby: ['a', 'b']
        // }
      },

    }
	}
</script>
