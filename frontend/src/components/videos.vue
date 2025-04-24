<template>

  <div class="container">
    <h1 class="title">广场舞教学视频广场</h1>

    <div class="body-container">
      <!-- 左侧视频区域 -->
      <div class="main-content">
        <div class="video-grid">
              <div class="video-item" v-for="(video ,index) in videos" :key="index">
                  <video controls class="thumbnail" :src="video.url">
                      Your browser does not support the video tag.
                  </video>
                <h3 class="video-title">{{video.title}}</h3>
                <p class="author">{{video.author}}</p>
              </div>

        </div>
      </div>

      <!-- 右侧功能区域 -->
      <div class="side-panel">
        <button class="action-btn share-btn">
          <span class="icon">📤</span>
          分享视频
        </button>
        <button class="action-btn invite-btn">
          <span class="icon">👥</span>
          邀请好友
        </button>
        <button class="action-btn collect-btn">
          <span class="icon">❤️</span>
          收藏视频
        </button>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'Videos',
  data() {
    return {
      videos: [
        {
          title: '苹果香广场舞',
          author: '李老师',
          url: "/dancing_videos/苹果香.mp4", // 使用项目 public 目录下的路径
        },
        {
          title: '苹果香广场舞',
          author: '李老师',
          url: "/dancing_videos/苹果香.mp4"
        },
        {
          title: '苹果香广场舞',
          author: '李老师',
          url: "/dancing_videos/苹果香.mp4"
        },
        {
          title: '苹果香广场舞',
          author: '李老师',
          url: "/dancing_videos/苹果香.mp4"
        },
        {
          title: '苹果香广场舞',
          author: '李老师',
          url: "/dancing_videos/苹果香.mp4"
        },
        {
          title: '苹果香广场舞',
          author: '李老师',
          url: "/dancing_videos/苹果香.mp4"
        },
        // 其他视频项...
      ],
    };
  },
};
</script>

<style scoped>
.container {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.title {
  text-align: center;
  color: #2c3e50;
  margin-bottom: 30px;
}

/* 修改 body-container 占满屏幕宽度 */
.body-container {
  display: flex;
  width: 100%; /* 占满父容器宽度 */
  justify-content: space-between; /* 左右分散排列 */
  gap: 20px; /* 间距 */
}

/* main-content 占 80% */
.main-content {
  flex: 0 0 80%; /* 固定 80% 宽度 */
}

/* side-panel 占 20% */
.side-panel {
  flex: 0 0 20%; /* 固定 20% 宽度 */
  position: sticky;
  top: 20px;
  height: fit-content;
  background: #f8f9fa;
  border-radius: 8px;
  padding: 20px;
}

/* 其余样式保持不变 */
.video-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 15px;
}

.video-item {
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  overflow: hidden;
}

.thumbnail {
  width: 100%;
  height: 180px;
  object-fit: cover;
}

.video-title {
  padding: 12px;
  font-size: 16px;
  margin: 0;
}

.author {
  padding: 0 12px 12px;
  color: #666;
  font-size: 14px;
}

.action-btn {
  display: flex;
  align-items: center;
  width: 100%;
  padding: 12px;
  margin-bottom: 15px;
  border: none;
  border-radius: 6px;
  background: #fff;
  box-shadow: 0 2px 6px rgba(0,0,0,0.1);
  cursor: pointer;
  transition: all 0.3s;
}

.action-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0,0,0,0.15);
}

.icon {
  margin-right: 10px;
  font-size: 18px;
}
</style>

