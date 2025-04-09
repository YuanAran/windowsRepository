<template>
  <div class="home-container">
    <h1>欢迎来到主页面</h1>
    <nav>
      <router-link to="/">首页</router-link>
    </nav>
  </div>
</template>

<script setup>
</script>

<style scoped>
.home-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  text-align: center;
}

h1 {
  color: #42b983;
  margin-bottom: 20px;
}

nav a {
  margin: 0 10px;
  color: #2c3e50;
  text-decoration: none;
}
</style>