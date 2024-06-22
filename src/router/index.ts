import { createRouter, createWebHistory } from "vue-router";
import type { RouteRecordRaw } from "vue-router";

import Home from "../views/Home.vue";
import Login from "../views/UserLogin.vue";

const routes: RouteRecordRaw[] = [
  { path: "/", component: Home },
  { path: "/login", component: Login },
  
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
