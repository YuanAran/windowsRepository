<template>
  <main class="community-view">
    <div class="header">
      <button class="back-btn" @click="goBack">
        <i class="fas fa-arrow-left"></i> 返回
      </button>
      <h1>舞友社区</h1>
    </div>
    
    <!-- 新增发帖表单 -->
    <div class="post-form">
      <textarea 
        v-model="newPostContent" 
        placeholder="分享你的舞蹈心得..."
        rows="3"
      ></textarea>
      <button 
        class="submit-btn" 
        @click="createPost"
        :disabled="!newPostContent || posting"
      >
        {{ posting ? '发布中...' : '发布' }}
      </button>
    </div>

    <div class="post-list">
      <div v-for="(post, index) in posts" :key="post.id" class="post-card">
        <div class="post-header">
          <img :src="post.avatar" alt="用户头像" class="avatar">
          <div class="user-info">
            <h3>{{ post.username }}</h3>
            <span class="post-time">{{ post.time }}</span>
          </div>
        </div>
        <div class="post-content">
          <p>{{ post.content }}</p>
        </div>
        <div class="post-actions">
          <button 
            class="like-btn" 
            @click="likePost(post.id)"
            :class="{ 'liked': post.userLiked }"
          >
            <i class="fas fa-thumbs-up"></i> {{ post.likes }}
          </button>
          <button 
            class="dislike-btn" 
            @click="dislikePost(post.id)"
            :class="{ 'disliked': post.userDisliked }"
          >
            <i class="fas fa-thumbs-down"></i> {{ post.dislikes }}
          </button>
          <button class="comment-btn">
            <i class="fas fa-comment"></i> {{ post.comments }}
          </button>
        </div>
      </div>
    </div>
  </main>
</template>

<script>
import axios from 'axios';

// 创建axios实例并配置baseURL
const api = axios.create({
  baseURL: 'http://localhost:8080', // 后端服务地址
  timeout: 5000
});

export default {
  name: 'CommunityView',
  data() {
    return {
      posts: [],
      newPostContent: '',
      posting: false,
      loading: false
    }
  },
  async created() {
    await this.fetchPosts();
  },
  methods: {
    async fetchPosts() {
      this.loading = true;
      try {
        const response = await api.get('/api/posts'); // 使用配置好的axios实例
        this.posts = response.data.data.map(post => ({
          ...post,
          time: this.formatTime(post.createdAt)
        }));
      } catch (error) {
        console.error('获取帖子失败:', error);
      } finally {
        this.loading = false;
      }
    },
    formatTime(timestamp) {
      const now = new Date();
      const postTime = new Date(timestamp);
      const diffHours = Math.floor((now - postTime) / (1000 * 60 * 60));
      
      if (diffHours < 1) return '刚刚';
      if (diffHours < 24) return `${diffHours}小时前`;
      return '一天前';
    },
    // 新增发帖方法
    async createPost() {
      if (!this.newPostContent.trim()) {
        alert('帖子内容不能为空');
        return;
      }
      
      this.posting = true;
      try {
        const response = await api.post('/api/posts', 
          JSON.stringify({ content: this.newPostContent }), // 使用JSON.stringify
          {
            headers: {
              'Authorization': `Bearer ${localStorage.getItem('token')}`,
              'Content-Type': 'application/json'
            }
          }
        );
        
        this.posts.unshift(response.data.data);
        this.newPostContent = '';
      } catch (error) {
        console.error('发帖失败:', error.response?.data);
        alert(`发帖失败: ${error.response?.data?.message || error.message}`);
      } finally {
        this.posting = false;
      }
    },

    // 修改点赞方法
    async likePost(postId) {
      try {
        const response = await axios.post(`/api/posts/${postId}/like`, null, {
          headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
          }
        });
        
        const postIndex = this.posts.findIndex(p => p.id === postId);
        if (postIndex !== -1) {
          this.posts[postIndex] = response.data.data;
        }
      } catch (error) {
        console.error('点赞失败:', error);
      }
    },

    // 修改点踩方法
    async dislikePost(postId) {
      try {
        const response = await axios.post(`/api/posts/${postId}/dislike`, null, {
          headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
          }
        });
        
        const postIndex = this.posts.findIndex(p => p.id === postId);
        if (postIndex !== -1) {
          this.posts[postIndex] = response.data.data;
        }
      } catch (error) {
        console.error('点踩失败:', error);
      }
    },
    goBack() {
      this.$router.push({ name: 'features' });
    }
  }
}
</script>

<style scoped>
.community-view {
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

.post-list {
  display: grid;
  gap: 2rem;
  margin-top: 2rem;
}

.post-card {
  padding: 1rem;
  background: #f5f5f5;
  border-radius: 8px;
}

.post-header {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.avatar {
  width: 50px;
  height: 50px;
  border-radius: 50%;
}

.user-info h3 {
  margin-bottom: 0.25rem;
}

.post-time {
  color: #666;
  font-size: 0.9rem;
}

.post-content {
  margin: 1rem 0;
}

.post-actions {
  display: flex;
  gap: 1rem;
}

.like-btn, .comment-btn {
  padding: 0.5rem 1rem;
  background: #42b983;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.like-btn:hover, .comment-btn:hover {
  background: #3aa876;
}

@media (max-width: 768px) {
  .post-header {
    flex-direction: column;
    align-items: flex-start;
  }
}
</style>

<style scoped>
/* 添加点踩按钮样式 */
.dislike-btn {
  padding: 0.5rem 1rem;
  background: #ff4757;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.dislike-btn:hover {
  background: #ff6b81;
}

/* 新增发帖表单样式 */
.post-form {
  margin-bottom: 2rem;
  background: #fff;
  padding: 1rem;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.post-form textarea {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  margin-bottom: 1rem;
  resize: vertical;
}

.submit-btn {
  padding: 0.5rem 1.5rem;
  background: #42b983;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  float: right;
}

.submit-btn:disabled {
  background: #ccc;
  cursor: not-allowed;
}

/* 修改点赞/踩按钮样式 */
.liked {
  background: #2ecc71 !important;
}

.disliked {
  background: #e74c3c !important;
}
</style>