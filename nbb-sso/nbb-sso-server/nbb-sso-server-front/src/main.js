import Vue from 'vue'
import App from './App.vue'

import Element from 'element-ui'
import './assets/styles/element-variables.scss'

import '@/assets/styles/index.scss' // global css
import '@/assets/styles/ruoyi.scss' // ruoyi css


import router from './router'

Vue.use(Element)

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router: router,
  render: h => h(App),
})
