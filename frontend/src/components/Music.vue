<template>
  <div class="music-container">
    <div class="header">
      <h1>广场舞音乐库</h1>
      <div class="search-bar">
        <input 
          type="text" 
          placeholder="搜索音乐..."
          v-model="searchQuery"
        />
        <button @click="searchMusic">搜索</button>
      </div>
    </div>
    
    <div class="music-list">
      <div 
        class="music-card" 
        v-for="music in filteredMusic" 
        :key="music.id"
        @click="playMusic(music)"
      >
        <div class="music-info">
          <h3>{{ music.title }}</h3>
          <p>{{ music.artist }}</p>
          <p class="duration">{{ music.duration }}</p>
        </div>
        <div class="play-icon">
          <span v-if="currentPlaying === music.id">▶</span>
        </div>
      </div>
    </div>
    
    <audio ref="audioPlayer" :src="currentAudio" @ended="onAudioEnd"></audio>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, nextTick } from 'vue';

const searchQuery = ref('');
const currentPlaying = ref(null);
const currentAudio = ref('');

const musicList = ref([]);

// 从后端获取音乐列表
const fetchMusicList = async () => {
  try {
    const response = await fetch('http://localhost:8080/api/music/list');
    const data = await response.json();
    musicList.value = data;
  } catch (error) {
    console.error('获取音乐列表失败:', error);
  }
};

// 组件挂载时获取数据
onMounted(() => {
  fetchMusicList();
});

const filteredMusic = computed(() => {
  return musicList.value.filter(music => 
    music.title.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
    music.artist.toLowerCase().includes(searchQuery.value.toLowerCase())
  );
});

function searchMusic() {
  // 搜索逻辑已通过computed属性实现
}

function playMusic(music) {
  currentPlaying.value = music.id;
  currentAudio.value = `http://localhost:8080/api/music/${music.id}`;
  nextTick(() => {
    const audioElement = document.querySelector('audio');
    
    // 检查支持的音频格式
    const supportedFormats = {
      'mp3': 'audio/mpeg',
      'ogg': 'audio/ogg',
      'wav': 'audio/wav'
    };
    
    // 获取文件扩展名
    const format = music.fileFormat || 'mp3';
    
    if (audioElement.canPlayType(supportedFormats[format])) {
      audioElement.play().catch(error => {
        console.error('播放失败:', error);
      });
    } else {
      console.error(`浏览器不支持${format}音频格式`);
    }
  });
}

function onAudioEnd() {
  currentPlaying.value = null;
}
</script>

<style scoped>
.music-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: var(--spacing-unit);
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.header {
  margin-bottom: 2rem;
}

.header h1 {
  color: var(--primary-color);
  margin-bottom: 1rem;
}

.search-bar {
  display: flex;
  gap: 1rem;
  margin-bottom: 1rem;
}

.search-bar input {
  flex: 1;
  padding: 0.5rem 1rem;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.search-bar button {
  padding: 0.5rem 1rem;
  background-color: var(--primary-color);
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.music-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 1.5rem;
}

.music-card {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
  background: white;
  border-radius: 8px;
  cursor: pointer;
  transition: transform 0.3s, box-shadow 0.3s;
}

.music-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
}

.music-info h3 {
  color: #2c3e50;
  margin-bottom: 0.5rem;
  font-weight: 600;
}

.music-info p {
  color: #2c3e50;
  opacity: 0.9;
  margin-bottom: 0.3rem;
}

.play-icon {
  font-size: 1.5rem;
  color: var(--primary-color);
}

@media (max-width: 768px) {
  .music-container {
    padding: calc(var(--spacing-unit) * 0.5);
  }
  
  .music-list {
    grid-template-columns: 1fr;
  }
}
</style>