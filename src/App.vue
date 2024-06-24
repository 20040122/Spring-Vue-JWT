<template>
  <div id="app">
    <router-view />
  </div>
</template>

<style scoped>
#app {
  height: 100vh;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.el-main {
  flex: 1;
  overflow: auto;
}
</style>

<style>
html,
body {
  height: 100%;
  margin: 0;
  padding: 0;
}
</style>
