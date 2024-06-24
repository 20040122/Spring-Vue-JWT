<template>
  <el-container class="profile-container">
    <el-header>
      <h2>个人中心</h2>
    </el-header>
    <el-main>
      <el-form :model="user" label-width="100px" class="profile-form">
        <el-form-item label="用户名">
          <el-input v-model="user.username" readonly />
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="user.phone_number" />
        </el-form-item>
        <el-form-item label="默认地址">
          <el-input v-model="user.default_address" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updateUserInfo">保存修改</el-button>
        </el-form-item>
      </el-form>
      <el-form label-width="100px" class="profile-form">
        <el-form-item label="当前密码">
          <el-input v-model="password.oldPassword" type="password" />
        </el-form-item>
        <el-form-item label="新密码">
          <el-input v-model="password.newPassword" type="password" />
        </el-form-item>
        <el-form-item label="确认新密码">
          <el-input v-model="confirmNewPassword" type="password" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updatePassword">修改密码</el-button>
        </el-form-item>
      </el-form>
    </el-main>
  </el-container>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { ElMessage } from "element-plus";
import { useUserStore } from "@/store/userStore";
import { updateUserProfile, updateUserPassword } from "@/global/utils";

const userStore = useUserStore();
// 用户信息 响应式双向绑定
const user = ref({
  username: "",
  phone_number: "",
  default_address: "",
});

const password = ref({
  oldPassword: "",
  newPassword: "",
});

const confirmNewPassword = ref("");

const updateUserInfo = async () => {
  const success = await updateUserProfile(user.value);
  if (success) {
    ElMessage.success("用户信息更新成功");
  }
};

const updatePassword = async () => {
  if (password.value.newPassword !== confirmNewPassword.value) {
    ElMessage.error("新密码和确认新密码不匹配");
    return;
  }

  const success = await updateUserPassword(password.value);
  if (success) {
    // 清空密码字段
    password.value.oldPassword = "";
    password.value.newPassword = "";
    confirmNewPassword.value = "";
  }
};

onMounted(() => {
  // 向当前 user 中赋值除了 token 之外的所有属性
  let { token, ...rest } = userStore.getUser;
  user.value = rest;
});
</script>

<style scoped>
.profile-container {
  width: 400px;
  margin: 100px auto;
  padding: 20px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.profile-form {
  max-width: 360px;
  margin: 0 auto;
}
</style>
