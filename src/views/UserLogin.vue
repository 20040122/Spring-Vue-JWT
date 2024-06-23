<template>
  <el-container class="login-container">
    <el-header>
      <h2>用户登录</h2>
    </el-header>
    <el-main>
      <el-form
        :model="loginForm"
        ref="loginFormRef"
        :rules="rules"
        label-width="80px"
        class="login-form"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="loginForm.username" autocomplete="off" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input
            type="password"
            v-model="loginForm.password"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">登录</el-button>
        </el-form-item>
      </el-form>
    </el-main>
  </el-container>
</template>

<script setup lang="ts">
import { ref, reactive } from "vue";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import { userLogIn } from "@/global/utils";

const router = useRouter();

const loginFormRef = ref<any>(null); // 显式指定 ref 的类型为 any
const loginForm = reactive({
  username: "",
  password: "",
});

const rules = {
  username: [{ required: true, message: "请输入用户名", trigger: "blur" }],
  password: [{ required: true, message: "请输入密码", trigger: "blur" }],
};

const onSubmit = async () => {
  if (loginFormRef.value) {
    // 进行表单验证
    const valid = await loginFormRef.value.validate();
    if (valid) {
      // 表单验证通过，执行登录请求
      const loginSuccess = await userLogIn(loginForm);
      if (loginSuccess) {
        router.push("/");
      }
    } else {
      ElMessage.error("表单验证失败");
    }
  }
};
</script>

<style scoped>
.login-container {
  width: 400px;
  margin: 100px auto;
  padding: 20px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.login-form {
  max-width: 360px;
  margin: 0 auto;
}

.el-header {
  text-align: center;
  margin-bottom: 20px;
}
</style>