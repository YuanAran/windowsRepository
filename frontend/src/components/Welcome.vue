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
<style scoped>
.welcome-container {
  font-family: 'Arial', sans-serif;
  max-width: 1400px; /* 增加页面最大宽度 */
  margin: 50px auto;
  padding: 30px;
  background-color: #fff;
  border-radius: 12px;
  box-shadow: 0 6px 15px rgba(0, 0, 0, 0.1);
  text-align: center;
}

h1 {
  font-size: 3.2em;
  color: #333;
  margin-bottom: 40px;
}

.feature-list {
  display: grid;
  grid-template-columns: repeat(2, 1fr); /* 每行显示两个功能项 */
  gap: 40px; /* 增加模块之间的间距 */
  margin-bottom: 40px;
}

.feature-item {
  background-color: #f9f9f9;
  padding: 25px;
  border-radius: 10px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  text-align: left; /* 使文本左对齐，便于阅读 */
}

.feature-item:hover {
  transform: translateY(-10px);
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.2);
}

.feature-item h3 {
  font-size: 1.8em;
  color: #333;
  margin-bottom: 15px;
}

.feature-item p {
  font-size: 1.2em;
  color: #777;
  line-height: 1.6;
  margin-bottom: 20px;
}

.welcome-actions {
  margin-top: 50px;
}

.start-button {
  display: inline-block;
  padding: 18px 35px;
  font-size: 1.3em;
  background-color: #f9d74e;
  color: white;
  text-decoration: none;
  border-radius: 8px;
  transition: background-color 0.3s ease, transform 0.3s ease;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.start-button:hover {
  background-color: #f7c130;
  transform: translateY(-2px);
}

.start-button:active {
  background-color: #f7c130;
  transform: translateY(1px);
}

/* 响应式设计 */
@media (max-width: 768px) {
  h1 {
    font-size: 2.4em;
  }

  .feature-list {
    grid-template-columns: 1fr; /* 小屏幕时单列显示 */
    gap: 20px;
  }

  .feature-item {
    padding: 20px;
  }

  .start-button {
    padding: 14px 28px;
    font-size: 1.1em;
  }
}
</style>




