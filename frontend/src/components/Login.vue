<template>
  <div class="login-container">
    <h2>登录</h2>
    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <label for="username">用户名</label>
        <input 
          type="text" 
          id="username" 
          v-model="username" 
          required
        />
      </div>
      <div class="form-group">
        <label for="password">密码</label>
        <input 
          type="password" 
          id="password" 
          v-model="password" 
          required
        />
      </div>
      <button type="submit">登录</button>
    </form>
    <p class="register-hint">若未注册请<router-link to="/register">注册</router-link></p>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { v4 as uuidv4 } from 'uuid';
const username = ref('');
const password = ref('');
const router = useRouter();
const sessionId = uuidv4();
const API_BASE_URL = 'http://localhost:8080/api';
const API_VPN_URL='http://1067150ru78fm.vicp.fun/api'
const API_NEW_URL='http://10.12.51.22:5555/api'
const API_NEW1_URL='https://10.12.51.22:5555/api'
const handleSubmit = async () => {
  try {
    const response = await fetch(`${API_NEW1_URL}/auth/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'include',
      body: JSON.stringify({
        username: username.value,
        password: password.value
      })
    });

    if (!response.ok) {
      const errorData = await response.json();
      throw new Error(errorData.message || '登录失败');
    }

    const data = await response.json();
    if (!data.token) {
      throw new Error('登录失败，请检查用户名和密码');
    }
    localStorage.setItem('token', data.token);
    console.log('登录成功:', data.token);
    await router.push('/dashboard');
  } catch (error) {
    console.error('登录错误:', error);
    if (error.response && error.response.data) {
      alert(`登录失败: ${error.response.data.message}`);
    } else if (error.message) {
      alert(`登录失败: ${error.message}`);
    } else {
      alert('登录失败，请检查用户名和密码');
    }
  }
};
</script>

<style scoped>
.login-container {
  font-family: 'Arial', sans-serif;
  max-width: 400px;
  margin: 50px auto;
  padding: 20px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

h2 {
  text-align: center;
  font-size: 2em;
  color: #333;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 20px;
}

label {
  font-size: 1.1em;
  color: #555;
  display: block;
  margin-bottom: 8px;
}

input {
  width: 100%;
  padding: 10px;
  font-size: 1em;
  border: 1px solid #ddd;
  border-radius: 4px;
  background-color: #f9f9f9;
  box-sizing: border-box;
  outline: none;
  transition: border-color 0.3s;
}

input:focus {
  border-color: #f9d74e;
}

button {
  width: 100%;
  padding: 12px;
  font-size: 1.2em;
  color: #fff;
  background-color: #f9d74e;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #f7c130;
}

.register-hint {
  text-align: center;
  margin-top: 15px;
  font-size: 1em;
}

.register-hint a {
  color: #f9d74e;
  text-decoration: none;
}

.register-hint a:hover {
  text-decoration: underline;
}
</style>
