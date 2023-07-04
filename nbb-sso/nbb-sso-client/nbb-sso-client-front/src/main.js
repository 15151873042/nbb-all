import Vue from 'vue'
import App from './App.vue'

import Element from 'element-ui'
import './assets/styles/element-variables.scss'

Vue.use(Element)

Vue.config.productionTip = false

import router from './router'

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
