<template>
  <el-container class="home-container">
    这是HOME页面
    // TODO 一个分类的商品列表（侧栏Menu一个分类复用一个商品展示组件（瀑布流卡片） 实现一个购物车 +商品详情弹出式
  </el-container>
</template>

<script setup lang="ts">

</script>

<style scoped>
.home-container {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

.el-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.user-info {
  display: flex;
  align-items: center;
}

.content {
  text-align: center;
}
</style>
