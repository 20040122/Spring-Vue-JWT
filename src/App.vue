<template>
  <div id="app" class="common-layout">
    <el-container direction="vertical">
      <Header />
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import Header from "@/components/Header.vue";
</script>

<style scoped>
.common-layout {
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