<template>
  <div>
    <span>张</span>
    <div class="mainPage">
      <div class="bg-box">
        <div class="main-bg">
          <img src="@/assets/people/main-bg-02.png"/>
        </div>
        <div class="bg-inner">
          <img src="@/assets/people/bg-01.png"/>
        </div>
        <div class="bg-inner">
          <img src="@/assets/people/bg-03.png"/>
        </div>
        <div class="bg-inner">
          <img src="@/assets/people/bg-04.png"/>
        </div>
        <div class="bg-inner">
          <img src="@/assets/people/bg-05.png"/>
        </div>
        <div class="bg-body-boy">
          <img class="bg-inner" src="@/assets/people/man-animate.png">
        </div>
      </div>
    </div>

    <!-- 底部tabbar -->
    <van-tabbar v-model="active">
      <van-tabbar-item name="3d" icon="home-o">
        <span>3D画像</span>
        <template #icon="{active}">
          <img :src="active ? icon.userActive : icon.userInactive" />
        </template>
      </van-tabbar-item>
      <van-tabbar-item name="health" icon="search">
        <span>健康档案</span>
        <template #icon="{active}">
          <img :src="active ? icon.healthActive : icon.healthInactive" />
        </template>
      </van-tabbar-item>
      <van-tabbar-item name="self" icon="friends-o">
        <span>自我检测</span>
        <template #icon="{active}">
          <img :src="active ? icon.selfActive : icon.selfInactive" />
        </template>
      </van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<script>
import userActive from '@/assets/images/icon-user-active.png'
import userInactive from '@/assets/images/icon-user-default.png'
import healthActive from '@/assets/images/icon-record-active.png'
import healthInactive from '@/assets/images/icon-record-default.png'
import selfActive from '@/assets/images/icon-monitor-active.png'
import selfInactive from '@/assets/images/icon-monitor-default.png'
export default {
  name: "index",

  data(){
    return {
      active: '3d',
      icon: {
        userActive,
        userInactive,
        healthActive,
        healthInactive,
        selfActive,
        selfInactive,
      },
    }
  }
}
</script>

<style lang="scss" scoped>
.mainPage {
  position: relative;
  background-color: lightpink;
  height: calc(100vh - 50px);

  .bg-box {
    position: absolute;
    width: 100%;
    height: 100%;
    z-index: 1;
    background-color: lightblue;
  }

  .main-bg {
    position: absolute;
    z-index: 1;
    width: 100%;
    height: 100%;
    overflow: hidden;
    > img {
      position: absolute;
      top: 7%;
      width: 100%;
      height: 100%; // 图片长度拉升
    }
  }

  .bg-inner {
    position: absolute;
    left: 0;
    bottom: 0;
    width: 100%;
    z-index: 2;
    >img {
      width: 100%;
    }
  }

  .bg-body-boy {
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    z-index: 3;
    .bg-inner{
      position: absolute;
      left: 0;
      top:50%;
      transform: translate(0,-50%);
      width: 100%;
      display: block;
    }
  }
}
</style>