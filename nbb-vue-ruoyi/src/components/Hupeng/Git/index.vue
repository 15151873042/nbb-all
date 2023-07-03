<template>
  <div>
    <svg-icon icon-class="github" @click="goto" />
  </div>
</template>

<script>
export default {
  name: 'HuPengGit',
  data() {
    return {
      url: 'https://github.com/15151873042/hp-all'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>