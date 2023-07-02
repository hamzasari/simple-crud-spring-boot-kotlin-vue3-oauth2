import Home from '@/components/Home';
import Todos from '@/components/Todos';
import { createRouter, createWebHistory } from 'vue-router';

const routes = [
  {
    path: '/',
    component: Home
  },
  {
    path: '/todos',
    component: Todos,
    meta: {
      requiresAuth: true
    }
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
})

export default router
