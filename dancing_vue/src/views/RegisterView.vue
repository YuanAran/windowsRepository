<template>
  <main class="register-view">
    <h1>用户注册</h1>
    <form @submit.prevent="handleRegister" class="register-form" enctype="multipart/form-data">
      <div class="form-group">
        <label for="username">用户名</label>
        <input type="text" id="username" v-model="form.username" required>
      </div>
      <div class="form-group">
        <label for="email">邮箱</label>
        <input type="email" id="email" v-model="form.email" required>
      </div>
      <div class="form-group">
        <label for="password">密码</label>
        <input type="password" id="password" v-model="form.password" required>
      </div>
      <div class="form-group">
        <label>头像</label>
        <input type="file" @change="handleAvatarChange" accept="image/*">
      </div>
      <button type="submit" class="submit-btn">注册</button>
    </form>
    <p class="login-link">
      已有账号？<router-link to="/login">立即登录</router-link>
    </p>
  </main>
</template>

<script>
import axios from 'axios';

export default {
  name: 'RegisterView',
  data() {
    return {
      form: {
        username: '',
        email: '',
        password: '',
        avatarUrl: null
      },
      avatarFile: null,
      loading: false,
      error: null
    }
  },
  methods: {
    handleAvatarChange(event) {
      this.avatarFile = event.target.files[0];
    },
    async handleRegister() {
      this.loading = true;
      this.error = null;
      
      try {
        const formData = new FormData();
        // 添加表单字段
        formData.append('username', this.form.username);
        formData.append('email', this.form.email); 
        formData.append('password', this.form.password);
        
        // 添加头像文件
        if (this.avatarFile) {
          formData.append('avatar', this.avatarFile);
        }

        const response = await axios.post('http://localhost:8080/api/auth/register', formData);
        // 接收后端的avatar_url字段
        localStorage.setItem('avatarUrl', response.data.data.avatar_url);
        localStorage.setItem('token', response.data.token);
        
        this.$swal.fire({
          title: '注册成功',
          text: '请使用您的账号登录',
          icon: 'success',
          confirmButtonColor: '#42b983'
        }).then(() => {
          this.$router.push({ name: 'login' });
        });
      } catch (error) {
        this.error = error.response?.data?.message || '注册失败，请重试';
        this.$swal.fire({
          title: '注册失败',
          text: this.error,
          icon: 'error',
          confirmButtonColor: '#ff4757'
        });
      } finally {
        this.loading = false;
      }
    }
  }
}
</script>

<style scoped>
.register-view {
  max-width: 400px;
  margin: 0 auto;
  padding: 2rem;
}

.register-form {
  margin-top: 2rem;
}

.form-group {
  margin-bottom: 1.5rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
}

.form-group input {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.submit-btn {
  width: 100%;
  padding: 0.75rem;
  background: #42b983;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
}

.submit-btn:hover {
  background: #3aa876;
}

.login-link {
  margin-top: 1.5rem;
  text-align: center;
}

.login-link a {
  color: #42b983;
  text-decoration: none;
}

.login-link a:hover {
  text-decoration: underline;
}
</style>