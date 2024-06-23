import { APIURL } from "./config";

import axios from "axios";
import { ElMessage } from "element-plus";
import { useUserStore } from "@/store/userStore";

export const userLogIn = async (loginForm: any): Promise<boolean> => {
  const userStore = useUserStore();

  try {
    const res = await axios.post(APIURL.login, loginForm);
    console.log(res.data);
    ElMessage.success("登录成功");
    // 处理登录成功逻辑
    userStore.setToken(res.data.token);
    return true;
  } catch (err) {
    console.error(err);
    ElMessage.error("登录失败");
    // 处理登录失败逻辑
    return false;
  }
};

export const userLogOut = async (): Promise<boolean> => {
  const userStore = useUserStore();

  try {
    await axios
      .post(APIURL.logout, {}, { headers: { token: userStore.getToken } })
      .then((res) => {
        console.log(res.data);
      });

    ElMessage.success("退出登录成功");
    // 处理退出登录成功逻辑
    userStore.clearToken();
    return true;
  } catch (err) {
    console.error(err);
    ElMessage.error("退出登录失败");
    // 处理退出登录失败逻辑
    return false;
  }
};
