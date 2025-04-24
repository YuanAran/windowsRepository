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
        <button @click="goBack" class="back-button">返回</button>
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

async function playMusic(music) {
  console.log('正在播放的音乐数据:', music);

  // 将 Proxy 对象转为普通对象，以便更容易访问属性
  const musicData = JSON.parse(JSON.stringify(music));

  console.log('musicData.url:', musicData.url);  // 打印 url 属性查看是否有效

  if (!musicData.url) {
    // 如果没有 URL，向后端请求资源
    try {
      const response = await fetch(`http://localhost:8080/api/music/${musicData.id}`);
      const data = await response.json();
      if (data.url) {
        musicData.url = data.url;  // 更新 musicData 中的 URL
        console.log('获取到的音频文件 URL:', musicData.url);
      } else {
        console.error('未能获取音频文件 URL');
        return;
      }
    } catch (error) {
      console.error('请求音频文件失败:', error);
      return;
    }
  }

  if (!musicData.url) {
    console.error('音频文件 URL 未找到');
    return;
  }

  console.log('准备播放的音频 URL:', musicData.url);

  const audioElement = document.querySelector('audio');
  if (!audioElement) {
    console.error('未找到音频元素');
    return;
  }

  // 如果点击的是当前正在播放的音乐，则切换播放/暂停状态
  if (currentPlaying.value === musicData.id) {
    if (audioElement.paused) {
      audioElement.play().catch(error => {
        console.error('播放失败:', error);
      });
    } else {
      audioElement.pause();
      currentPlaying.value = null;
    }
    return;
  }

  currentPlaying.value = musicData.id;
  
  // 确保URL格式正确
  let audioUrl = musicData.url;
  if (!audioUrl.startsWith('http')) {
    audioUrl = `http://localhost:3000/${encodeURIComponent(musicData.title + '-' + musicData.artist + '.mp3')}`;
  }
  
  currentAudio.value = audioUrl;
  const format = musicData.format || 'mp3';

  // 设置音频源并播放
  audioElement.src = currentAudio.value;
  audioElement.type = supportedFormats[format];
  audioElement.load();
  
  audioElement.play().catch(error => {
    console.error('播放失败:', error);
  });
}

function onAudioEnd() {
  currentPlaying.value = null;
}

function goBack() {
  window.history.back();
}
</script>


<style scoped>
.music-container {
  font-family: 'Arial', sans-serif;
  background-color: #f4f4f9;
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.header {
  text-align: center;
  margin-bottom: 30px;
}

h1 {
  font-size: 2.5em;
  color: #333;
}

.search-bar {
  margin-top: 15px;
  display: flex;
  justify-content: center;
}

.search-bar input {
  width: 300px;
  padding: 10px;
  font-size: 1em;
  border: 1px solid #ddd;
  border-radius: 5px;
  outline: none;
}

.search-bar button {
  padding: 10px 15px;
  font-size: 1em;
  margin-left: 10px;
  background-color: #f9d74e;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.search-bar button:hover {
  background-color: #f7c130;
}

.back-button {
  margin-left: 10px;
  background-color: #f4f4f9;
  border: 1px solid #ddd;
}

.back-button:hover {
  background-color: #e0e0e0;
}

.music-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 20px;
  margin-top: 20px;
}

.music-card {
  background-color: #fff;
  padding: 15px;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: transform 0.2s ease, box-shadow 0.3s ease;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.music-card:hover {
  transform: translateY(-10px);
  box-shadow: 0 6px 15px rgba(0, 0, 0, 0.15);
}

.music-info {
  flex: 1;
}

.music-info h3 {
  font-size: 1.2em;
  color: #333;
  margin: 0;
}

.music-info p {
  font-size: 1em;
  color: #777;
  margin-top: 5px;
}

.play-icon {
  font-size: 1.5em;
  color: #f9d74e;
}

.play-icon span {
  display: inline-block;
  transform: scale(1.3);
}

</style>



