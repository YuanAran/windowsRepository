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

const username = ref('');
const password = ref('');
const router = useRouter();

const handleSubmit = async () => {
  try {
    const response = await fetch('http://localhost:8080/api/auth/login', {
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
    router.push('/dashboard');
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
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  width: 200%;
  max-width: 500px;
  margin: 0 auto;
  background: rgba(255, 255, 255, 0.9);
  padding: 2rem;
}

.login-container::before {
  content: '';
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: -1;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-container::before {
  content: '';
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: -1;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-container h2 {
  color: #2c3e50;
  margin-bottom: 2rem;
  font-size: 2.2rem;
  text-align: center;
  font-weight: 600;
}

.form-group {
  margin-bottom: 1.8rem;
  text-align: center;
  width: 100%;
  max-width: 400px;
}

label {
  display: block;
  margin-bottom: 0.8rem;
  color: #2c3e50;
  font-size: 1.1rem;
  font-weight: 500;
}

input {
  width: 100%;
  padding: 1.2rem;
  border: 1px solid #e2e8f0;
  border-radius: 12px;
  font-size: 1rem;
  font-weight: bold;
  color: #1a365d;
  transition: all 0.3s;
  background: rgba(255, 255, 255, 0.8);
}

input[type='password'] {
  color: #2c3e50;
  font-weight: 600;
}

input:focus {
  outline: none;
  border-color: var(--primary-color);
  box-shadow: 0 0 0 3px rgba(66, 185, 131, 0.2);
}

button {
  width: 100%;
  padding: 1rem;
  background-color: var(--primary-color);
  color: white;
  border: none;
  border-radius: 30px;
  font-weight: bold;
  font-size: 1.1rem;
  cursor: pointer;
  transition: all 0.3s;
}

button:hover {
  transform: translateY(-3px);
  box-shadow: 0 6px 20px rgba(66, 185, 131, 0.4);
}

.register-hint {
  margin-top: 1.5rem;
  text-align: center;
  color: #2c3e50;
}

.register-hint a {
  color: #1a365d;
  text-decoration: none;
  font-weight: 600;
}

.register-hint a:hover {
  text-decoration: underline;
}
button {
  width: 100%;
  padding: 1.2rem;
  background: linear-gradient(to right, var(--primary-color), var(--secondary-color));
  color: #2c3e50;
  border: none;
  border-radius: 12px;
  font-weight: 600;
  font-size: 1.1rem;
  cursor: pointer;
  transition: all 0.3s;
  box-shadow: 0 4px 15px rgba(66, 185, 131, 0.3);
}
</style>