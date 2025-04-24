<template>
  <div class="register-container">
    <h2>注册</h2>
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
        <label for="email">邮箱</label>
        <input 
          type="email" 
          id="email" 
          v-model="email" 
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
      <div class="form-group">
        <label for="confirmPassword">确认密码</label>
        <input 
          type="password" 
          id="confirmPassword" 
          v-model="confirmPassword" 
          required
        />
      </div>
      <button type="submit">注册</button>
    </form>
    <p class="login-hint">若已有账户请<router-link to="/login">登录</router-link></p>
  </div>
</template>

<style scoped>
.login-hint {
  color: #1a365d;
  font-weight: 600;
}
</style>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const username = ref('');
const email = ref('');
const password = ref('');
const confirmPassword = ref('');
const router = useRouter();

const handleSubmit = async () => {
  try {
    
    const response = await fetch('http://localhost:8080/api/auth/register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'include',
      body: JSON.stringify({
        username: username.value,
        email: email.value,
        password: password.value
      })
    });

    if (!response.ok) {
      throw new Error('注册失败');
    }

    router.push('/login');
  } catch (error) {
    console.error('注册错误:', error);
    alert('注册失败，请重试');
  }
};
</script>

<style scoped>
.register-container {
  font-family: 'Arial', sans-serif;
  max-width: 500px;
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
  margin-bottom: 30px;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  font-weight: bold;
  color: #555;
  margin-bottom: 5px;
}

.form-group input {
  width: 100%;
  padding: 12px;
  font-size: 1em;
  border: 1px solid #ddd;
  border-radius: 5px;
  background-color: #f9f9f9;
  box-sizing: border-box;
  outline: none;
  transition: border-color 0.3s ease;
}

.form-group input:focus {
  border-color: #f9d74e;
}

button {
  width: 100%;
  padding: 12px;
  font-size: 1.1em;
  background-color: #f9d74e;
  border: none;
  border-radius: 5px;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #f7c130;
}

.login-hint {
  text-align: center;
  margin-top: 20px;
  font-size: 1em;
  color: #777;
}

.login-hint a {
  color: #f9d74e;
  text-decoration: none;
  transition: color 0.3s ease;
}

.login-hint a:hover {
  color: #f7c130;
}
</style>
