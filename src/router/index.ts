import { createRouter, createWebHistory } from 'vue-router';
import DefaultLayout from '@/layout/DefaultLayout.vue';
import Home from '@/views/Home.vue';
import UserLogin from '@/views/UserLogin.vue';
import UserProfile from '@/views/UserProfile.vue';

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
      {
        path: 'profile',
        name: 'UserProfile',
        component: UserProfile,
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;