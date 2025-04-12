<template>
  <div class="welcome-container">
    <h1>欢迎来到舞蹈应用</h1>
    
    <div class="feature-list">
      <div class="feature-item">
        <h3>舞蹈学习</h3>
        <p>海量舞蹈教学视频，从入门到精通</p>
      </div>
      <div class="feature-item">
        <h3>音乐库</h3>
        <p>精选舞蹈音乐，节奏分析功能</p>
      </div>
      <div class="feature-item">
        <h3>社区</h3>
        <p>与舞友交流心得，分享舞蹈视频</p>
      </div>
      <div class="feature-item">
        <h3>邀请好友</h3>
        <p>邀请好友一起学习，获得奖励</p>
      </div>
    </div>
    
    <div class="welcome-actions">
      <router-link to="/login" class="start-button">立即开始</router-link>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
</script>

<style scoped>
.welcome-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  width: 300%;
  margin: 0;
  transform: translateX(-33.33%);
  padding: 2rem 0;
  background: rgba(255, 255, 255, 0.9);
  box-sizing: border-box;
}

.welcome-container::before {
  content: '';
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: -1;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.welcome-container h1 {
  color: #1a202c;
  margin-bottom: 2rem;
  font-size: 2.5rem;
  text-align: center;
  font-weight: 600;
}

.welcome-description {
  color: #1a202c;
  font-size: 1.2rem;
  margin-bottom: 3rem;
  text-align: center;
}

.feature-list {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 2rem;
  margin-bottom: 3rem;
  width: 100%;
  max-width: 1200px;
  padding: 0 1rem;
}

.feature-item {
  background: rgba(255, 255, 255, 0.95);
  padding: 1.5rem;
  border-radius: 12px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.feature-item h3 {
  color: var(--primary-color);
  margin-bottom: 0.8rem;
}

.feature-item p {
  color: #2d3748;
  font-size: 1rem;
  text-align: center;
}

.welcome-actions {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1.5rem;
}

.start-button {
  background-color: var(--primary-color);
  color: #1a202c;
  padding: 1rem 2.5rem;
  border-radius: 30px;
  font-weight: bold;
  font-size: 1.2rem;
  text-decoration: none;
  transition: all 0.3s;
  text-align: center;
}

.start-button:hover {
  background-color: var(--secondary-color);
  transform: translateY(-2px);
}
</style>