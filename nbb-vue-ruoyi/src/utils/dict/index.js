import {mergeOptions} from './DictOptions'
import {isBlank} from "@/utils/ruoyi";
import Dict from "@/utils/dict/Dict";

// 字典插件处理
export default {
  install(Vue, options) {
    mergeOptions(options) // 合并字典处理配置信息


    Vue.mixin({
      data() {
        if (isBlank(this.$options) || isBlank(this.$options.dicts)) {
          return {}
        }

        const dict = new Dict()
        dict.owner = this
        return {
          dict
        }
      },

      created() {
        if (!(this.dict instanceof Dict)) {
          return
        }
        options.onCreated && options.onCreated(this.dict)
        this.dict.init(this.$options.dicts).then(() => {
          options.onReady && options.onReady(this.dict)
          this.$nextTick(() => {
            this.$emit('dictReady', this.dict)
            if (this.$options.methods && this.$options.methods.onDictReady instanceof Function) {
              this.$options.methods.onDictReady.call(this, this.dict)
            }
          })
        })
      },
    })
  }
}