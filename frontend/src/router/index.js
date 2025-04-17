import { createRouter, createWebHistory } from 'vue-router'
import Login from '../components/login.vue'
import Register from '../components/register.vue'
import Welcome from '../components/Welcome.vue'
import Music from '../components/Music.vue'
import Dashboard from '../components/Dashboard.vue'

const routes = [
  {
    path: '/',
    component: Welcome
  },
  {
    path: '/login',
    component: Login
  },
  {
    path: '/register',
    component: Register
  },
  {
    path: '/music',
    component: Music
  },
  {
    path: '/dashboard',
    component: Dashboard 
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router