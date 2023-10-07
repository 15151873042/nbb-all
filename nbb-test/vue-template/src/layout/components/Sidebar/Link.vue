<template>
  <component :is="type" v-bind="linkProps(to)">
    <slot/>
  </component>
</template>


<script>
import {isExternal} from "@/utils/validate";

export default {
  name: "Link",
  props: {
    // 跳转url
    to: {
      type: String,
      required: true
    }
  },

  computed: {
    // 是否是外部链接
    isExternal() {
      return isExternal(this.to)
    },

    // 组件名称（路由还是a标签）
    type() {
      if (this.isExternal) {
        return 'a'
      } else {
        return 'router-link'
      }
    }
  },

  methods: {
    linkProps(to) {
      if (this.isExternal) {
        return {
          href: to,
          target: '_blank',
          rel: 'noopener'
        }
      }
      return {
        to: to
      }
    }
  }
}
</script>


<style scoped>

</style>
