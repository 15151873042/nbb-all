<template>
  <div :class="classObj" class="app-wrapper">
    <!-- 如果当前是手机端登录的，当侧边栏展开的时候，添加一个全局遮罩，点击之后，则隐藏侧边栏 -->
    <div v-if="device==='mobile'&&sidebar.opened" class="drawer-bg" @click="handleClickOutside"/>
    <!-- 侧边栏 -->
    <sidebar v-if="!sidebar.hide" class="sidebar-container" />
    <!-- 主区域 -->
    <div class="main-container">
      <!-- 顶栏 -->
      <div :class="{'fixed-header': fixedHeader}">
        <!-- 导航栏 -->
        <Navbar></Navbar>
        <!-- 标签视图 -->
        <TagsView v-if="needTagsView"></TagsView>
      </div>
      <!-- 页面 -->
      <AppMain></AppMain>
    </div>
  </div>
</template>

<script>
import {mapState} from "vuex";
import {Navbar, Sidebar, TagsView, AppMain} from './components'
export default {
  name: "Layout",

  components: {
    Sidebar,
    Navbar,
    TagsView,
    AppMain
  },

  computed: {
    ...mapState({
      theme: state => state.settings.theme, // 当前主题颜色
      fixedHeader: state => state.settings.fixedHeader, // 是否固定顶栏
      needTagsView: state => state.settings.tagsView,
      sidebar: state => state.app.sidebar, // 侧边栏配置信息
      device: state => state.app.device,
    }),


    classObj() {
      return {
        hideSidebar: !this.sidebar.opened,
        openSidebar: this.sidebar.opened,
        withoutAnimation: this.sidebar.withoutAnimation,
        mobile: false
      }
    },
  },

  methods: {
    handleClickOutside() {
      this.$store.dispatch('app/closeSideBar', { withoutAnimation: false })
    }
  },
}
</script>

<style lang="scss" scoped>
@import "~@/assets/styles/mixin.scss";
@import "~@/assets/styles/variables.scss";

.app-wrapper {
  @include clearfix;
  position: relative;
  height: 100%;
  width: 100%;

  &.mobile.openSidebar {
    position: fixed;
    top: 0;
  }
}

.drawer-bg {
  background: #000;
  opacity: 0.3;
  width: 100%;
  top: 0;
  height: 100%;
  position: absolute;
  z-index: 999;
}

.fixed-header {
  position: fixed;
  top: 0;
  right: 0;
  z-index: 9;
  width: calc(100% - #{$base-sidebar-width});
  transition: width 0.28s;
}

.hideSidebar .fixed-header {
  width: calc(100% - 54px);
}

.sidebarHide .fixed-header {
  width: 100%;
}

.mobile .fixed-header {
  width: 100%;
}
</style>
