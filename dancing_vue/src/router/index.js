import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import FeatureSelection from '../views/FeatureSelection.vue'
import LoginView from '../views/LoginView.vue'
import RegisterView from '../views/RegisterView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView  // 直接使用导入的HomeView
  },
  {
    path: '/features',
    name: 'features',
    component: FeatureSelection
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/register',
    name: 'register',
    component: RegisterView
  },
  {
    path: '/tutorials',
    name: 'tutorials',
    component: () => import('@/views/TutorialsView.vue')
  },
  {
    path: '/music',
    name: 'music',
    component: () => import('@/views/MusicView.vue')
  },
  {
    path: '/videos',
    name: 'videos',
    component: () => import('@/views/VideosView.vue')
  },
  {
    path: '/community',
    name: 'community',
    component: () => import('@/views/CommunityView.vue')
  },
  {
    path: '/features',
    name: 'features',
    component: () => import('@/views/FeatureSelection.vue')
  },
  // 在routes数组中添加
  {
    path: '/user-detail',
    name: 'user-detail',
    component: () => import('@/views/UserDetail.vue')
  }
]

// 替换 process.env.BASE_URL 为
const router = createRouter({
  history: createWebHistory(import.meta.env.VITE_APP_BASE_URL || '/'),
  routes
})

export default router
