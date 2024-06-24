import axios from 'axios';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/store/userStore';
import { ElMessage } from 'element-plus';

// 创建 Axios 实例
const axiosInstance = axios.create({
  baseURL: '/api', // 根据需要设置基础 URL
  timeout: 10000, // 请求超时时间
});

// 设置请求拦截器
axiosInstance.interceptors.request.use(
  (config) => {
    const userStore = useUserStore();
    const token = userStore.getToken;
    if (token) {
      config.headers['token'] = token;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 设置响应拦截器
axiosInstance.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    const router = useRouter();
    const userStore = useUserStore();

    if (error.response && error.response.status === 401) {
      ElMessage.error('认证失败，请重新登录');
      userStore.clearUser(); // 清除用户信息
      router.push('/login'); // 跳转到登录页面
    }
    return Promise.reject(error);
  }
);

export default axiosInstance;