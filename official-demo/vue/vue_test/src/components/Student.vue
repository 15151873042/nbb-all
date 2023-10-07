<template>
	<div>
    <h2>{{msg}}</h2>
		<h2>学生姓名：{{userInfo.name}}</h2>
		<h2>学生性别：{{userInfo.sex}}</h2>
		<h2>学生年龄：{{userInfo.age}}</h2>
		<button @click="updateName">尝试修改收到的姓名</button>
		<button @click="updateUserInfo">尝试修改学生信息</button>
		<button @click="updateMsg">修改msg</button>
	</div>
</template>

<script>
	export default {
		name:'Student',
		methods: {
			updateName(){
				this.userInfo.name = this.userInfo.name + 1
			},
      updateUserInfo(){
        this.userInfo = {
          name: '张三',
          sex: '男',
          age: 20
        }
      },

      updateMsg(){
        this.msg = this.msg + 1
      },
		},


		//接收的同时对数据：进行类型限制+默认值的指定+必要性的限制
		props:['userInfo', 'msg']
	}
</script>
