import { defineStore } from 'pinia'
import { ref } from 'vue'
import api from '@/api'

export const useUserStore = defineStore('user', () => {
  const username = ref(null)

  async function login(usernameInput, passwordInput) {
    const response = await api.post('/api/auth/login', { 
      username: usernameInput, 
      password: passwordInput 
    })
    username.value = usernameInput
    localStorage.setItem('username', usernameInput)
  }

  return { username, login }
})