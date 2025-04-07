<template>
  <div class="home-page">
    <header class="header">
      <h1>居家广场舞</h1>
      <p>让舞蹈成为您生活的一部分</p>
    </header>

    <section class="auth-section">
      <div class="auth-buttons">
        <router-link to="/login" class="auth-btn login-btn">
          <i class="fas fa-sign-in-alt"></i> 登录
        </router-link>
        <router-link to="/register" class="auth-btn register-btn">
          <i class="fas fa-user-plus"></i> 注册
        </router-link>
      </div>
    </section>

    <section class="features">
      <div class="feature-card">
        <i class="fas fa-music"></i>
        <h3>丰富音乐库</h3>
        <p>精选热门广场舞音乐，满足不同风格需求</p>

      </div>
      <div class="feature-card">
        <i class="fas fa-video"></i>
        <h3>高清教学视频</h3>
        <p>专业舞蹈老师指导，轻松学习各种舞步</p>
      </div>
      <div class="feature-card">
        <i class="fas fa-users"></i>
        <h3>舞友社区</h3>
        <p>与全国舞友交流心得，分享舞蹈乐趣</p>
      </div>
    </section>
  </div>
</template>

<script>
// 确保组件名称与导入名称一致
export default {
  name: 'HomePage'  // 这个名称应该与导入时的名称一致
}
</script>

<style scoped>
.home-page {
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem;
}

.header {
  text-align: center;
  padding: 4rem 0;
}

.header h1 {
  font-size: 3rem;
  color: #2c3e50;
  margin-bottom: 1rem;
}

.header p {
  font-size: 1.2rem;
  color: #666;
}

.features {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 2rem;
  margin: 4rem 0;
}

.feature-card {
  text-align: center;
  padding: 2rem;
  background: #f5f5f5;
  border-radius: 8px;
}

.feature-card i {
  font-size: 2rem;
  color: #42b983;
  margin-bottom: 1rem;
}

.feature-card h3 {
  font-size: 1.5rem;
  margin-bottom: 0.5rem;
}

.feature-card p {
  color: #666;
}

.auth-section {
  margin: 2rem 0;
  text-align: center;
}

.auth-buttons {
  display: flex;
  justify-content: center;
  gap: 2rem;
  max-width: 800px;
  margin: 0 auto;
}

.auth-btn {
  padding: 1rem 2rem;
  border-radius: 4px;
  text-decoration: none;
  font-size: 1.2rem;
  transition: all 0.3s ease;
  display: inline-flex;
  align-items: center;
  gap: 0.5rem;
}

.login-btn {
  background: #42b983;
  color: white;
}

.register-btn {
  background: #3498db;
  color: white;
}

.login-btn:hover {
  background: #3aa876;
}

.register-btn:hover {
  background: #2980b9;
}

@media (max-width: 768px) {
  .features {
    grid-template-columns: 1fr;
  }

  .header h1 {
    font-size: 2rem;
  }
  
  .auth-buttons {
    flex-direction: column;
  }
}
</style>