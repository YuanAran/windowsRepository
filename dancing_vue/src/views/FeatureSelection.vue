<template>
  <main class="feature-selection">
    <div class="user-info" @click="viewUserDetail">
      <span>{{ user.name || '游客' }}</span>
    </div>
    
    <h1>欢迎使用居家广场舞</h1>
    <div class="feature-list">
      <div class="feature-card" @click="selectFeature('music')">
        <i class="fas fa-music"></i>
        <h3>音乐库</h3>
      </div>
      <div class="feature-card" @click="selectFeature('videos')">
        <i class="fas fa-video"></i>
        <h3>教学视频</h3>
      </div>
      <div class="feature-card" @click="selectFeature('community')">
        <i class="fas fa-users"></i>
        <h3>舞友社区</h3>
      </div>
    </div>
  </main>
</template>

<script>
export default {
  data() {
    return {
      user: {
        name: localStorage.getItem('username') || ''
      }
    }
  },
  methods: {
    selectFeature(feature) {
      this.$router.push({ name: feature });
    },
    viewUserDetail() {
      this.$router.push({ name: 'user-detail' });
    }
  }
}
</script>

<style scoped>
.feature-selection {
  padding: 2rem;
  max-width: 1200px;
  margin: 0 auto;
}

.feature-list {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 2rem;
  margin-top: 2rem;
}

.feature-card {
  padding: 2rem;
  background: #f5f5f5;
  border-radius: 8px;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s ease;
}

.feature-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.feature-card i {
  font-size: 2rem;
  color: #42b983;
  margin-bottom: 1rem;
}

@media (max-width: 768px) {
  .feature-list {
    grid-template-columns: 1fr;
  }
}

.user-info {
  position: absolute;
  top: 20px;
  right: 20px;
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
  padding: 8px 12px;
  border-radius: 20px;
  background: rgba(255,255,255,0.8);
  transition: all 0.3s ease;
}

.user-info:hover {
  background: rgba(255,255,255,0.9);
}

.avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  object-fit: cover;
}
</style>