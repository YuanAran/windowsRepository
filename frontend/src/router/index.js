import { createRouter, createWebHistory } from 'vue-router'
import Login from '../components/Login.vue'
import Register from '../components/register.vue'
import Welcome from '../components/Welcome.vue'
import Music from '../components/Music.vue'
import Dashboard from '../components/Dashboard.vue'
import videos from "../components/videos.vue"
import Profile from '../components/Profile.vue'
import VideoCon from "../components/VideoCon.vue";

const routes = [
  {
    path: '/',
    component: Welcome
  },
    {
      path: '/video',
      component: VideoCon
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
  },
  {
    path: '/videos',
    component: videos

  },
  {
    path: '/profile',
    component: Profile
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
