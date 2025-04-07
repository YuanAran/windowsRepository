<template>
  <div class="user-detail">
    <div class="user-header">
      <h2>{{ user.name }}</h2>
    </div>
    
    <div class="user-info">
      <div class="info-item">
        <label>用户名:</label>
        <span>{{ user.name }}</span>
      </div>
      <div class="info-item">
        <label>注册时间:</label>
        <span>{{ user.registerDate || '2023-01-01' }}</span>
      </div>
    </div>
    
    <button @click="logout" class="logout-btn">退出登录</button>
  </div>
</template>

<script>
export default {
  name: 'UserDetail',
  data() {
    return {
      user: {
        name: localStorage.getItem('username') || '游客',
        registerDate: localStorage.getItem('registerDate') || ''
      }
    }
  },
  methods: {
    logout() {
      localStorage.removeItem('username')
      this.$router.push({ name: 'login' })
    }
  }
}
</script>

<style scoped>
.user-detail {
  max-width: 600px;
  margin: 2rem auto;
  padding: 2rem;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
}

.user-header {
  text-align: center;
  margin-bottom: 2rem;
}

.avatar {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  object-fit: cover;
  margin-bottom: 1rem;
}

.user-info {
  margin: 2rem 0;
}

.info-item {
  display: flex;
  justify-content: space-between;
  padding: 1rem 0;
  border-bottom: 1px solid #eee;
}

.logout-btn {
  display: block;
  width: 100%;
  padding: 0.75rem;
  background: #ff4757;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
  margin-top: 2rem;
}

.logout-btn:hover {
  background: #ff6b81;
}
</style>