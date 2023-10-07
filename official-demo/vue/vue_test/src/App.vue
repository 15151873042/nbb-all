<template>
	<div>
		<Student :userInfo="userInfo" :msg="msg"/>
	</div>
</template>

<script>
	import Student from './components/Student'

	export default {
		name:'App',
		components:{Student},

    data() {
      return {
        userInfo: {
          name: '李四',
          sex: '女',
          age: 18
        },

        msg: '你好'
      }
    }
	}
</script>
