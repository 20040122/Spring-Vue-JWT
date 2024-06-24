import { APIURL } from "./config";
import axiosInstance from "./axiosConfig";
import { ElMessage } from "element-plus";
import { useUserStore } from "@/store/userStore";

// 用户注册
export const userRegister = async (registerForm: any): Promise<boolean> => {
  console.log(registerForm);
  try {
    const res = await axiosInstance.post(APIURL.register, registerForm);
    console.log(res.data);

    ElMessage.success("注册成功,请登录");
    // 处理注册成功逻辑
    return true;
  } catch (err) {
    console.error(err);
    ElMessage.error("注册失败");
    // 处理注册失败逻辑
    return false;
  }
};

// 用户登录
export const userLogIn = async (loginForm: any): Promise<boolean> => {
  const userStore = useUserStore();

  try {
    const res = await axiosInstance.post(APIURL.login, loginForm);
    console.log(res.data);
    ElMessage.success("登录成功");
    // 处理登录成功逻辑
    userStore.setUser(res.data);
    return true;
  } catch (err) {
    console.error(err);
    ElMessage.error("登录失败");
    // 处理登录失败逻辑
    return false;
  }
};

// 退出登录
export const userLogOut = async (): Promise<boolean> => {
  const userStore = useUserStore();

  try {
    const res = await axiosInstance.post(APIURL.logout);
    console.log(res.data);

    ElMessage.success("退出登录成功");
    // 处理退出登录成功逻辑
    userStore.clearUser();
    return true;
  } catch (err) {
    console.error(err);
    ElMessage.error("退出登录失败");
    // 处理退出登录失败逻辑
    return false;
  }
};

// 更新用户信息
export const updateUserProfile = async (profileForm: any): Promise<boolean> => {
  try {
    const res = await axiosInstance.post(APIURL.updateUser, profileForm);
    console.log(res.data);

    ElMessage.success("更新用户信息成功");
    // 处理更新用户信息成功逻辑
    return true;
  } catch (err) {
    console.error(err);
    ElMessage.error("更新用户信息失败");
    // 处理更新用户信息失败逻辑
    return false;
  }
};

// 更新用户密码
export const updateUserPassword = async (
  passwordForm: any
): Promise<boolean> => {
  try {
    const res = await axiosInstance.post(APIURL.updatePassword, passwordForm);
    console.log(res.data);

    ElMessage.success("更新密码成功");
    // 处理更新密码成功逻辑
    return true;
  } catch (err) {
    console.error(err);
    ElMessage.error("更新密码失败");
    // 处理更新密码失败逻辑
    return false;
  }
};
