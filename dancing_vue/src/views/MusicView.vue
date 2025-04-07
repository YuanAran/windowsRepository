<template>
  <main class="music-view">
    <div class="header">
      <button class="back-btn" @click="goBack">
        <i class="fas fa-arrow-left"></i> 返回
      </button>
      <h1>音乐库</h1>
    </div>
    <div class="music-list">
      <div v-for="(music, index) in musicList" :key="index" class="music-card">
        <div class="music-info">
          <h3>{{ music.title }}</h3>
          <p>{{ music.artist }}</p>
          <div class="music-meta">
            <span><i class="fas fa-clock"></i> {{ music.duration }}</span>
            <span><i class="fas fa-headphones"></i> {{ music.plays }} 次播放</span>
          </div>
        </div>
        <button class="play-btn" @click="playMusic(music)">
          <i class="fas fa-play"></i> 播放
        </button>
      </div>
    </div>
  </main>
</template>

<script>
export default {
  name: 'MusicView',
  data() {
    return {
      musicList: [
        {
          title: '小苹果',
          artist: '筷子兄弟',
          duration: '3:45',
          plays: 12345,
          url: '#'
        },
        {
          title: '最炫民族风',
          artist: '凤凰传奇',
          duration: '4:12',
          plays: 9876,
          url: '#'
        },
        {
          title: '荷塘月色',
          artist: '凤凰传奇',
          duration: '3:58',
          plays: 5678,
          url: '#'
        }
      ]
    }
  },
  methods: {
    playMusic(music) {
      console.log('正在播放:', music.title);
      // 这里可以添加播放音乐的逻辑
    },
    goBack() {
      this.$router.push({ name: 'features' });
    },
  }
}
</script>

<style scoped>
.music-view {
  padding: 2rem;
  max-width: 1200px;
  margin: 0 auto;
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

.music-list {
  display: grid;
  gap: 1rem;
  margin-top: 2rem;
}

.music-card {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
  background: #f5f5f5;
  border-radius: 8px;
}

.music-info h3 {
  margin-bottom: 0.5rem;
}

.music-meta {
  display: flex;
  gap: 1rem;
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
  .music-card {
    flex-direction: column;
    align-items: flex-start;
  }
}
</style>