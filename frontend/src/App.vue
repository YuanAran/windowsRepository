<template>
  <div>
    <nav>
      <router-link to="/login">登录</router-link> | 
      <router-link to="/register">注册</router-link>
    </nav>
    <router-view />
  </div>
</template>

<script setup>
</script>

<style>
nav {
  padding: 20px;
  text-align: center;
}
nav a {
  margin: 0 10px;
  color: #42b983;
  text-decoration: none;
}
</style>