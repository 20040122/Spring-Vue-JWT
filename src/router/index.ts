import { createRouter, createWebHistory } from 'vue-router';
import DefaultLayout from '@/layout/DefaultLayout.vue';
import Home from '@/views/Home.vue';
import UserLogin from '@/views/UserLogin.vue';

const routes = [
  {
    path: '/',
    component: DefaultLayout,
    children: [
      {
        path: '',
        name: 'Home',
        component: Home,
      },
      {
        path: 'login',
        name: 'UserLogin',
        component: UserLogin,
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;