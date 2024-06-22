<template>
  <div id="app">
    <el-container class="app-container">
      <el-header class="header">
        <el-page-header @back="goBack">
          <template #breadcrumb>
            <el-breadcrumb separator="/">
              <el-breadcrumb-item to="/">首页</el-breadcrumb-item>
              <el-breadcrumb-item>当前页面</el-breadcrumb-item>
            </el-breadcrumb>
          </template>
          <template #content>
            <div class="page-title">生鲜速递</div>
          </template>
        </el-page-header>
        <div class="user-info">
          <div>
            <span class="text-large font-600 mr-3">欢迎您,</span>
            <span
              class="text-sm mr-2"
              style="color: var(--el-text-color-regular)"
            >
              {{user.name}}
            </span>
            <el-button type="primary" @click="handleLogin">登录</el-button>
          </div>
        </div>
      </el-header>
      <el-main class="main">
        <router-view />
      </el-main>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from "vue-router";
import { ref } from "vue";
import {
  ElPageHeader,
  ElBreadcrumb,
  ElBreadcrumbItem,
  ElButton,
  ElTag,
} from "element-plus";
import { useUserStore } from "@/store/userStore";

const router = useRouter();
const userStore = useUserStore();

let user = ref(userStore.getUserInfo);
console.log(user.value);

const goBack = () => {
  router.back();
};

const handleLogin = () => {
  router.push("/login");
};
</script>

<style scoped>
.app-container {
  height: 100vh;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.main {
  padding: 20px;
}

.page-title {
  font-size: 24px;
  font-weight: bold;
}

.user-info {
  display: flex;
  align-items: center;
}
</style>
