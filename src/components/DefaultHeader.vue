<template>
  <el-header class="header">
    <el-page-header @back="goBack">
      <template #content>
        <div class="page-title">生鲜速递</div>
      </template>
    </el-page-header>
    <div class="user-info">
      <div>
        <template v-if="isLoggedIn">
          <el-text class="mx-1">欢迎您,</el-text>
          <el-text class="mx-1" type="primary">{{ user.username }}</el-text>
          <el-divider direction="vertical" />
          <el-button type="primary" @click="goToProfile">个人中心</el-button>
          <el-button type="danger" @click="onLogout">退出登录</el-button>
        </template>
        <template v-else>
          <el-text class="mx-1">您好，请登录</el-text>
          <el-divider direction="vertical" />
          <el-button type="primary" @click="handleLogin">登录</el-button>
        </template>
      </div>
    </div>
  </el-header>
</template>

<script setup>
import { useRouter } from "vue-router";
import { computed } from "vue";
import { ElMessage } from "element-plus";
import { useUserStore } from "@/store/userStore";
import { userLogOut } from "@/global/utils";

const router = useRouter();
const userStore = useUserStore();

const isLoggedIn = computed(() => userStore.getIsLoggedIn);
const user = computed(() => userStore.getUser);

const goBack = () => {
  router.back();
};

const handleLogin = () => {
  router.push("/login");
};

const onLogout = async () => {
  const logoutSuccess = await userLogOut();
  if (logoutSuccess) {
    router.push("/");
  }
};

const goToProfile = () => {
  router.push("/profile");
};
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 80px;
  width: 100%; /* 确保 header 占满宽度 */
}

.user-info {
  display: flex;
  align-items: center;
}

.page-title {
  font-size: 20px;
  font-weight: bold;
}
</style>
