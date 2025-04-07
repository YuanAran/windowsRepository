<template>
  <main class="videos-view">
    <div class="header">
      <button class="back-btn" @click="goBack">
        <i class="fas fa-arrow-left"></i> 返回
      </button>
      <h1>教学视频</h1>
    </div>
    <div class="video-list">
      <div v-for="(video, index) in videos" :key="index" class="video-card">
        <div class="video-thumbnail">
          <img :src="video.thumbnail" :alt="video.title">
        </div>
        <div class="video-info">
          <h3>{{ video.title }}</h3>
          <p>{{ video.description }}</p>
          <div class="video-meta">
            <span><i class="fas fa-clock"></i> {{ video.duration }}</span>
            <span><i class="fas fa-eye"></i> {{ video.views }} 次观看</span>
          </div>
          <button class="play-btn" @click="playVideo(video)">
            <i class="fas fa-play"></i> 立即观看
          </button>
        </div>
      </div>
    </div>
  </main>
</template>

<script>
export default {
  name: 'VideosView',
  data() {
    return {
      videos: [
        {
          title: '小苹果基础教学',
          description: '学习小苹果的基本舞步和节奏',
          duration: '15分钟',
          views: 1234,
          thumbnail: 'https://via.placeholder.com/300x200',
          url: '#'
        },
        {
          title: '最炫民族风进阶',
          description: '掌握最炫民族风的复杂动作',
          duration: '20分钟',
          views: 987,
          thumbnail: 'https://via.placeholder.com/300x200',
          url: '#'
        },
        {
          title: '荷塘月色完整版',
          description: '学习完整的荷塘月色舞蹈',
          duration: '25分钟',
          views: 567,
          thumbnail: 'https://via.placeholder.com/300x200',
          url: '#'
        }
      ]
    }
  },
  methods: {
    playVideo(video) {
      // 这里可以添加播放视频的逻辑
      console.log('正在播放:', video.title);
      // window.location.href = video.url;
    },
    goBack() {
      this.$router.push({ name: 'features' });
    },
  }
}
</script>

<style scoped>
.videos-view {
  padding: 2rem;
  max-width: 1200px;
  margin: 0 auto;
}

.video-list {
  display: grid;
  gap: 2rem;
  margin-top: 2rem;
}

.video-card {
  display: flex;
  gap: 1rem;
  padding: 1rem;
  background: #f5f5f5;
  border-radius: 8px;
}

.video-thumbnail img {
  width: 300px;
  height: 200px;
  object-fit: cover;
  border-radius: 4px;
}

.video-info {
  flex: 1;
}

.video-meta {
  display: flex;
  gap: 1rem;
  margin: 0.5rem 0;
  color: #666;
}

.play-btn {
  padding: 0.5rem 1rem;
  background: #42b983;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.play-btn:hover {
  background: #3aa876;
}

@media (max-width: 768px) {
  .video-card {
    flex-direction: column;
  }

  .video-thumbnail img {
    width: 100%;
    height: auto;
  }
}

.header {
  display: flex;
  align-items: center;
  gap: 1rem;
  margin-bottom: 2rem;
}

.back-btn {
  padding: 0.5rem 1rem;
  background: #42b983;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.back-btn:hover {
  background: #3aa876;
}
</style>