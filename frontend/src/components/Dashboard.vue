<template>
  <div class="home-container">
    <div class="hero-banner">
      <h1>欢迎来到居家广场舞</h1>
      <p class="subtitle">一起跳舞，享受健康生活</p>
    </div>
    
    <div class="feature-cards">
      <div class="card" @click="$router.push('/videos')">
        <h3>学习视频</h3>
        <p>专业广场舞教学视频</p>
      </div>
      
      <div class="card" @click="$router.push('/music')">
        <h3>音乐库</h3>
        <p>精选广场舞音乐</p>
      </div>
      
      <div class="card" @click="$router.push('/community')">
        <h3>社区</h3>
        <p>与舞友交流分享</p>
      </div>
      
      <div class="card" @click="$router.push('/invite')">
        <h3>邀请好友</h3>
        <p>一起学习广场舞</p>
      </div>
    </div>
  </div>
</template>

<script setup>
</script>

<style scoped>
.home-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: var(--spacing-unit);
  text-align: center;
  transition: all 0.3s ease;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.hero-banner {
  background: linear-gradient(rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.5)), url('@/assets/dance-bg.jpg');
  background-size: cover;
  background-position: center;
  padding: 4rem 2rem;
  border-radius: 8px;
  margin-bottom: 2rem;
  color: white;
}

h1 {
  color: #2c3e50;
  margin-bottom: 1rem;
  font-size: 2.5rem;
  transition: color 0.3s;
  font-weight: 600;
}

.subtitle {
  font-size: 1.2rem;
  opacity: 0.9;
}

.feature-cards {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 1.5rem;
  margin-top: 2rem;
}

.card {
  background: white;
  padding: 1.5rem;
  border-radius: 8px;
  cursor: pointer;
  transition: transform 0.3s, box-shadow 0.3s;
}

.card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
}

.card h3 {
  color: #2c3e50;
  margin-bottom: 0.5rem;
  font-weight: 600;
}

.card p {
  color: #2c3e50;
  opacity: 0.9;
}

nav a {
  margin: 0 calc(var(--spacing-unit) * 1.5);
  color: var(--secondary-color);
  text-decoration: none;
  font-weight: bold;
  transition: color 0.3s;
}

nav a:hover {
  color: var(--primary-color);
}

@media (max-width: 768px) {
  .home-container {
    padding: calc(var(--spacing-unit) * 0.5);
  }
  
  nav a {
    margin: 0 calc(var(--spacing-unit) * 0.5);
    display: block;
    padding: calc(var(--spacing-unit) * 0.5) 0;
  }
}
</style>