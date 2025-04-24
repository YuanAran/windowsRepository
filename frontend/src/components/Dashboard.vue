<template>
  <div class="home-container">
  <div class="user-icon" @click="showUserEmail">
    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
      <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
      <circle cx="12" cy="7" r="4"></circle>
    </svg>
  </div>
  <div class="friends-list">
    <h3>好友列表</h3>
    <div class="search-box">
      <input 
        v-model="searchKeyword"
        placeholder="输入用户ID搜索"
      />
      <button class="search-button" @click="searchUsers">搜索</button>
      <!-- 搜索结果部分 -->
      <div v-if="searchResults && searchResults.data" class="search-results">
        <div class="user-item">
          <span>用户ID：{{ searchResults.data.id }}</span>
          <span>用户名：{{ searchResults.data.username }}</span>
          <span>邮箱：{{ searchResults.data.email }}</span>
          <button @click="sendFriendRequest(searchResults.data.id)">添加好友</button>
        </div>
      </div>
    </div>
    <ul>
      <li v-for="friend in friends" :key="friend.id">
        {{ friend.friend.username }}
      </li>
      <li v-if="friends.length === 0">暂无好友</li>
    </ul>

    <!-- 好友请求部分 -->
    <div class="friend-requests">
      <h4>好友请求</h4>
      <ul v-if="friendRequests.length > 0">
        <li v-for="request in friendRequests" :key="request.user.id">
          {{ request.user.username }}
          <button @click="acceptRequest(request.user.id)">接受</button>
        </li>
      </ul>
      <p v-else>暂无好友申请</p> <!-- 如果没有好友请求，显示此提示 -->
    </div>
  </div>
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
      
      <div class="card" @click="$router.push('/video')">
        <h3>邀请好友</h3>
        <p>一起学习广场舞</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import FriendService from '../services/FriendService';

const router = useRouter();

const friends = ref([]);
const friendRequests = ref([]);
const searchKeyword = ref('');
const searchResults = ref([]);

onMounted(async () => {
  try {
    // 处理好友列表
    friends.value = await FriendService.getFriends() || []; // 确保赋值为空数组
    // 处理好友请求
    friendRequests.value = await FriendService.getFriendRequests() || []; // 确保赋值为空数组
    console.log('好友请求数据:', friendRequests.value);
    console.log('好友列表数据:', friends.value);
  } catch (error) {
    console.error('加载好友列表失败:', error);
    // 如果出现错误，确保 friendRequests 是空数组
    friendRequests.value = [];
  }
});


const showUserEmail = async () => {
  router.push('/profile');
};

const searchUsers = async () => {
  if (searchKeyword.value.trim() === '') {
    searchResults.value = {};  // 初始化为空对象或空数组
    return;
  }

  // 验证输入是否为数字ID
  if (!/^\d+$/.test(searchKeyword.value)) {
    alert('请输入有效的用户ID(数字)');
    return;
  }

  try {
    const result = await FriendService.searchUsers(searchKeyword.value);
    searchResults.value = result || {};
    console.log('搜:', result);// 确保返回的是有效数据
    console.log('搜索结果:', searchResults.value); // 调试日志
  } catch (error) {
    console.error('搜索用户失败:', error);
    searchResults.value = {};  // 如果出现错误，确保 searchResults 是空对象
  }
};


const sendFriendRequest = async (friendId) => {
  try {
    await FriendService.sendFriendRequest(friendId);
    searchResults.value = {};  // 发送请求后清空搜索结果
    searchKeyword.value = '';  // 清空搜索框
  } catch (error) {
    console.error('发送好友请求失败:', error);
  }
};


const acceptRequest = async (requestId) => {
  try {
    await FriendService.acceptFriendRequest(requestId);
    // 从 friendRequests 中移除已接受的请求
    friendRequests.value = friendRequests.value.filter(r => r.user.id !== requestId);
    // 更新好友列表
    friends.value = await FriendService.getFriends() || []; // 确保是一个有效的数组
  } catch (error) {
    console.error('接受好友请求失败:', error);
  }
};

</script>

<style scoped>
.home-container {
  font-family: 'Arial', sans-serif;
  display: grid;
  grid-template-columns: 300px 1fr;
  grid-template-areas: "friends main";
  padding: 40px;
  background-color: #f4f4f9;
  min-height: 100vh;
  gap: 40px;
  max-width: 1400px;
  margin: 0 auto;
}

.user-icon {
  position: absolute;
  top: 20px;
  right: 20px;
  cursor: pointer;
  transition: transform 0.2s ease-in-out;
}

.friends-list {
  background-color: #fff;
  border-radius: 12px;
  box-shadow: 0px 6px 15px rgba(0, 0, 0, 0.1);
  padding: 20px;
  width: 250px;
  grid-area: friends;
  border: 2px solid red; /* 临时调试样式 */
}

.friends-list h3 {
  font-size: 1.5em;
  color: #333;
  margin-bottom: 15px;
}

.friends-list ul {
  list-style: none;
  padding: 0;
}

.friends-list li {
  padding: 10px 0;
  border-bottom: 1px solid #eee;
}

.search-result-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
}

.user-info {
  display: flex;
  flex-direction: column;
}

.user-id, .username, .user-email {
  margin: 2px 0;
}

.user-id {
  font-size: 0.8em;
  color: #777;
}

.username {
  font-weight: bold;
}

.user-email {
  font-size: 0.9em;
  color: #555;
}

.add-button {
  background: none;
  border: none;
  font-size: 1.5em;
  color: #f9d74e;
  cursor: pointer;
  padding: 0 10px;
}

.add-button:hover {
  color: #f7c130;
}

.user-icon:hover {
  transform: scale(1.1);
}

.hero-banner {
  text-align: center;
  margin-top: 40px;
  background-color: #f9d74e;
  padding: 40px 30px;
  border-radius: 12px;
  box-shadow: 0px 6px 20px rgba(0, 0, 0, 0.1);
  max-width: 100%;
  width: 100%;
  margin-bottom: 60px;
}

.hero-banner h1 {
  font-size: 3em;
  color: #333;
  margin: 0;
  font-weight: bold;
}

.hero-banner .subtitle {
  font-size: 1.4em;
  color: #555;
  margin-top: 15px;
}

.feature-cards {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 30px;
  margin-top: 20px;
  width: 100%;
  max-width: 800px;
  justify-items: center;
  grid-area: main;
}

.card {
  background-color: #fff;
  border-radius: 12px;
  box-shadow: 0px 6px 15px rgba(0, 0, 0, 0.1);
  padding: 30px;
  cursor: pointer;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  text-align: center;
  width: 100%;
  max-width: 300px;
  margin: 0 auto;
}

.card:hover {
  transform: translateY(-12px);
  box-shadow: 0px 8px 20px rgba(0, 0, 0, 0.15);
}

.card h3 {
  font-size: 2em;
  color: #333;
  margin: 0;
  font-weight: bold;
}

.card p {
  font-size: 1.1em;
  color: #777;
  margin-top: 12px;
  line-height: 1.6;
}

/* 适配小屏幕 */
@media (max-width: 768px) {
  .hero-banner h1 {
    font-size: 2.4em;
  }

  .hero-banner .subtitle {
    font-size: 1.2em;
  }

  .feature-cards {
    gap: 20px;
  }

  .card {
    padding: 25px;
  }
}

@media (max-width: 480px) {
  .hero-banner h1 {
    font-size: 2em;
  }

  .hero-banner .subtitle {
    font-size: 1.1em;
  }

  .feature-cards {
    gap: 15px;
  }

  .card {
    padding: 20px;
  }
}
</style>
