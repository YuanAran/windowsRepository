<template>
  <div class="profile-container">
    <div class="profile-header">
      <h1>个人中心</h1>
    </div>
    
    <div class="profile-content">
      <div class="profile-info">
        <div class="info-item">
          <span class="label">账户:</span>
          <span class="value">{{ user.username }}</span>
        </div>
        <div class="info-item">
          <span class="label">邮箱:</span>
          <span class="value">{{ user.email }}</span>
        </div>
        <div class="info-item">
          <span class="label">ID:</span>
          <span class="value">{{ user.id }}</span>
        </div>
      </div>
      
      <div class="profile-actions">
        <button @click="goBack" class="action-button back-button">返回</button>
        <button @click="showChangePassword" class="action-button">修改密码</button>
        <button @click="logout" class="action-button logout-button">退出登录</button>
      </div>
    </div>
    
    <div v-if="showPasswordForm" class="password-form">
      <h3>修改密码</h3>
      <form @submit.prevent="changePassword">
        <div class="form-group">
          <label for="currentPassword">当前密码</label>
          <input 
            type="password" 
            id="currentPassword" 
            v-model="password.current" 
            required
          />
        </div>
        <div class="form-group">
          <label for="newPassword">新密码</label>
          <input 
            type="password" 
            id="newPassword" 
            v-model="password.new" 
            required
          />
        </div>
        <div class="form-group">
          <label for="confirmPassword">确认新密码</label>
          <input 
            type="password" 
            id="confirmPassword" 
            v-model="password.confirm" 
            required
          />
        </div>
        <div class="form-actions">
          <button type="submit" class="submit-button">确认修改</button>
          <button type="button" @click="cancelChangePassword" class="cancel-button">取消</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const user = ref({
  username: '',
  email: '',
  id: ''
});
const API_BASE_URL = 'http://localhost:8080/api';
const API_VPN_URL='http://1067150ru78fm.vicp.fun/api'
const API_NEW_URL='http://10.12.51.22:5555/api'
const API_NEW1_URL='https://10.12.51.22:5555/api'
const fetchUserInfo = async () => {
  try {
    const response = await fetch(`${API_NEW1_URL}/auth/user-info`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    });
    
    if (response.ok) {
      const data = await response.json();
      user.value = {
        username: data.username,
        email: data.email,
        id: data.id
      };
    }
  } catch (error) {
    console.error('获取用户信息出错:', error);
  }
};

fetchUserInfo();

const showPasswordForm = ref(false);
const password = ref({
  current: '',
  new: '',
  confirm: ''
});

const showChangePassword = () => {
  showPasswordForm.value = true;
};

const cancelChangePassword = () => {
  showPasswordForm.value = false;
  password.value = { current: '', new: '', confirm: '' };
};

const changePassword = async () => {
  if (password.value.new !== password.value.confirm) {
    alert('两次输入的新密码不一致');
    return;
  }
  
  try {
    const response = await fetch(`${API_NEW1_URL}/auth/change-password`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      },
      body: JSON.stringify({
        currentPassword: password.value.current,
        newPassword: password.value.new
      })
    });
    
    if (response.ok) {
      alert('密码修改成功');
      cancelChangePassword();
    } else {
      const error = await response.json();
      alert(error.message || '密码修改失败');
    }
  } catch (error) {
    console.error('修改密码出错:', error);
    alert('网络错误，请稍后重试');
  }
};

const goBack = () => {
  router.go(-1);
};

const logout = () => {
  localStorage.removeItem('token');
  localStorage.removeItem('email');
  router.push('/login');
};
</script>
<style scoped>
.profile-container {
  font-family: 'Arial', sans-serif;
  max-width: 800px;
  margin: 30px auto;
  padding: 20px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.profile-header {
  text-align: center;
  margin-bottom: 20px;
}

.profile-header h1 {
  font-size: 2.5em;
  color: #333;
}

.profile-content {
  padding: 20px;
}

.profile-info {
  margin-bottom: 20px;
}

.info-item {
  margin-bottom: 15px;
  display: flex;
  justify-content: space-between;
}

.label {
  font-weight: bold;
  color: #555;
}

.value {
  color: #333;
}

.profile-actions {
  display: flex;
  justify-content: space-between;
  margin-top: 30px;
}

.action-button {
  padding: 10px 20px;
  font-size: 1.1em;
  background-color: #f9d74e;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.action-button:hover {
  background-color: #f7c130;
}

.back-button {
  background-color: #6c757d;
}

.back-button:hover {
  background-color: #5a6268;
}

.logout-button {
  background-color: #ff4d4d;
}

.logout-button:hover {
  background-color: #e64040;
}

.password-form {
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  margin-top: 30px;
}

.password-form h3 {
  font-size: 2em;
  color: #333;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  font-weight: bold;
  color: #555;
  margin-bottom: 5px;
}

.form-group input {
  width: 100%;
  padding: 10px;
  font-size: 1em;
  border: 1px solid #ddd;
  border-radius: 5px;
  background-color: #f9f9f9;
  box-sizing: border-box;
  outline: none;
  transition: border-color 0.3s;
}

.form-group input:focus {
  border-color: #f9d74e;
}

.form-actions {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.submit-button {
  padding: 10px 20px;
  font-size: 1.1em;
  background-color: #4caf50;
  border: none;
  border-radius: 5px;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.submit-button:hover {
  background-color: #45a049;
}

.cancel-button {
  padding: 10px 20px;
  font-size: 1.1em;
  background-color: #ff4d4d;
  border: none;
  border-radius: 5px;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.cancel-button:hover {
  background-color: #e64040;
}
</style>

