import modal from "@/plugins/modal";

export default {
  install(Vue) {
    Vue.prototype.$modal = modal
  }
}