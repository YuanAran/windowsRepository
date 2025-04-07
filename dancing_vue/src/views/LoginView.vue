<script setup>
import { ref } from 'vue'
import { useUserStore } from '@/stores/user'
import { useRouter } from 'vue-router'

const userStore = useUserStore()
const router = useRouter()
const username = ref('')
const password = ref('')

const login = async () => {
  try {
    await userStore.login(username.value, password.value)
    router.push({ name: 'features' }) // 修改为跳转到feature-selection
  } catch (error) {
    alert(error.message)
  }
}
</script>

<template>
  <div class="login-container">
    <div class="login-form">
      <h2>用户登录</h2>
      <div class="form-group">
        <label for="username">用户名</label>
        <input 
          id="username" 
          v-model="username" 
          type="text" 
          placeholder="请输入用户名"
          class="form-input"
        >
      </div>
      <div class="form-group">
        <label for="password">密码</label>
        <input 
          id="password" 
          v-model="password" 
          type="password" 
          placeholder="请输入密码"
          class="form-input"
        >
      </div>
      <button @click="login" class="login-btn">登 录</button>
      <div class="register-link">
        没有账户？<router-link to="/register">立即注册</router-link>
      </div>
    </div>
  </div>
</template>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #f5f5f5;
}

.login-form {
  width: 400px;
  padding: 40px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.login-form h2 {
  text-align: center;
  margin-bottom: 30px;
  color: #333;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  font-weight: 500;
  color: #555;
}

.form-input {
  width: 100%;
  padding: 12px 15px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 16px;
  transition: border-color 0.3s;
}

.form-input:focus {
  border-color: #409eff;
  outline: none;
}

.login-btn {
  width: 100%;
  padding: 12px;
  background-color: #409eff;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.login-btn:hover {
  background-color: #66b1ff;
}

.register-link {
  margin-top: 20px;
  text-align: center;
  color: #666;
}

.register-link a {
  color: #409eff;
  text-decoration: none;
  font-weight: 500;
}

.register-link a:hover {
  text-decoration: underline;
}
</style>