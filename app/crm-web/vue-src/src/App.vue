<template>
    <div>
      <title-bar></title-bar>
      <router-view></router-view>
    </div>
</template>

<script>
  import TitleBar from './components/title-bar'
    export default {
        name: "app",
      data () {
          return {

          }
      },
      components : {
        TitleBar
      }
    }
</script>

<style scoped>

</style>
