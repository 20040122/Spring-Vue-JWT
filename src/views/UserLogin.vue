<template>
  <el-container class="auth-container">
    <el-header>
      <h2>用户认证</h2>
    </el-header>
    <el-main>
      <el-tabs v-model="activeTab" type="card">
        <el-tab-pane label="登录" name="login">
          <el-form
            :model="loginForm"
            ref="loginFormRef"
            :rules="loginRules"
            label-width="80px"
            class="auth-form"
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
              <el-button type="primary" @click="onSubmitLogin">登录</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="注册" name="register">
          <el-form
            :model="registerForm"
            ref="registerFormRef"
            :rules="registerRules"
            label-width="80px"
            class="auth-form"
          >
            <el-form-item label="用户名" prop="username">
              <el-input v-model="registerForm.username" autocomplete="off" />
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input
                type="password"
                v-model="registerForm.password"
                autocomplete="off"
              />
            </el-form-item>
            <el-form-item label="默认地址" prop="default_address">
              <el-input
                v-model="registerForm.default_address"
                autocomplete="off"
              />
            </el-form-item>
            <el-form-item label="手机号" prop="phone_number">
              <el-input v-model="registerForm.phone_number" autocomplete="off" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmitRegister"
                >注册</el-button
              >
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </el-main>
  </el-container>
</template>

<script setup lang="ts">
import { ref, reactive } from "vue";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import { userLogIn, userRegister } from "@/global/utils";

const router = useRouter();

const activeTab = ref<string>("login");

// 登录表单
const loginFormRef = ref<any>(null); // 显式指定 ref 的类型为 any
const loginForm = reactive({
  username: "",
  password: "",
});

const loginRules = {
  username: [{ required: true, message: "请输入用户名", trigger: "blur" }],
  password: [{ required: true, message: "请输入密码", trigger: "blur" }],
};

const onSubmitLogin = async () => {
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

// 注册表单
const registerFormRef = ref<any>(null); // 显式指定 ref 的类型为 any
const registerForm = reactive({
  username: "",
  password: "",
  phone_number: "",
  default_address: "",
});

const registerRules = {
  username: [{ required: true, message: "请输入用户名", trigger: "blur" }],
  password: [{ required: true, message: "请输入密码", trigger: "blur" }],
  defaultAddress: [
    { required: true, message: "请输入默认地址", trigger: "blur" },
  ],
  phoneNumber: [{ required: true, message: "请输入手机号", trigger: "blur" }],
};

const onSubmitRegister = async () => {
  if (registerFormRef.value) {
    // 进行表单验证
    const valid = await registerFormRef.value.validate();
    if (valid) {
      // 表单验证通过，执行注册请求
      const registerSuccess = await userRegister(registerForm);
      if (registerSuccess) {
        activeTab.value = "login"; // 切换到登录选项卡
      }
    } else {
      ElMessage.error("表单验证失败");
    }
  }
};
</script>

<style scoped>
.auth-container {
  width: 400px;
  margin: 100px auto;
  padding: 20px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.auth-form {
  max-width: 360px;
  margin: 0 auto;
}

.el-header {
  text-align: center;
  margin-bottom: 20px;
}
</style>
