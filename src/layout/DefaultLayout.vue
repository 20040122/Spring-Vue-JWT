<template>
  <div class="common-layout">
    <el-container direction="vertical">
      <Header />
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import Header from "@/components/DefaultHeader.vue";
</script>

<style scoped>
.layout-container {
  height: 100vh;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}
</style>
