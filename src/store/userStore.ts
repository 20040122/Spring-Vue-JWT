import { defineStore } from "pinia";

interface TokenState {
  token: string | null;
  isLoggedIn: boolean;
}

export const useUserStore = defineStore("token", {
  state: (): TokenState => ({
    token: localStorage.getItem("token"),
    isLoggedIn: !!localStorage.getItem("token"), // 根据 token 的有无初始化登录状态
  }),
  getters: {
    // !在Vue3和Pinia中，getter调用起来是属性而不是方法
    getToken: (state): string | null => state.token,
    getIsLoggedIn: (state): boolean => state.isLoggedIn,
    getUserInfo: (state): { name: string; age: number } => {
      // TODO 返回用户信息
      return {
        name: "张三",
        age: 18,
      };
    },
  },
  actions: {
    setToken(token: string) {
      this.token = token;
      this.isLoggedIn = true; // 登录状态为 true
      localStorage.setItem("token", token);
    },
    clearToken() {
      this.token = null;
      this.isLoggedIn = false; // 登录状态为 false
      localStorage.removeItem("token");
    },
    // 手动设置登录状态,在请求后端接口后使用
    updateLoginStatus(isLoggedIn: boolean) {
      this.isLoggedIn = isLoggedIn;
    },
  },
});
