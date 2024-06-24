import { defineStore } from "pinia";

interface User {
  username: string;
  default_address: string | null;
  phone_number: string;
  token: string;
}

export const useUserStore = defineStore("user", {
  state: () => ({
    user: localStorage.getItem("user")
      ? JSON.parse(localStorage.getItem("user")!)
      : null,
    isLoggedIn: !!localStorage.getItem("user"), // 根据 user 的有无初始化登录状态
  }),
  getters: {
    getUser: (state) => state.user,
    getIsLoggedIn: (state) => state.isLoggedIn,
    getToken: (state) => state.user?.token || null, // 获取token的getter
  },
  actions: {
    setUser(data: { token: string; user: Omit<User, 'token'> }) {
      const user = { ...data.user, token: data.token };
      this.user = user;
      this.isLoggedIn = true; // 登录状态为 true
      localStorage.setItem("user", JSON.stringify(user));
    },
    clearUser() {
      this.user = null;
      this.isLoggedIn = false; // 登录状态为 false
      localStorage.removeItem("user");
    },
    updateToken(token: string) {
      if (this.user) {
        this.user.token = token;
        localStorage.setItem("user", JSON.stringify(this.user));
      }
    },
    clearToken() {
      if (this.user) {
        this.user.token = '';
        localStorage.setItem("user", JSON.stringify(this.user));
      }
    },
  },
});
