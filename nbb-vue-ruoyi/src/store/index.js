import Vue from 'vue'
import Vuex from 'vuex'
import user from './modules/user'
import dict from './modules/dict'
import settings from './modules/settings'
import app from './modules/app'
import permission from "@/store/modules/permission";
import tagsView from "@/store/modules/tagsView";
import getters from "@/store/getters";


Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    app,
    user,
    dict,
    settings,
    tagsView,
    permission
  },
  getters
})

export default store