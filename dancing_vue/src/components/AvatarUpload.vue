<template>
  <div class="avatar-upload">
    <img v-if="imageUrl" :src="imageUrl" class="avatar">
    <input type="file" @change="handleFileChange" accept="image/*">
    <button @click="uploadAvatar">上传头像</button>
  </div>
</template>

<script>
export default {
  props: ['userId'],
  data() {
    return {
      imageFile: null,
      imageUrl: null
    }
  },
  methods: {
    handleFileChange(e) {
      const file = e.target.files[0];
      if (file) {
        this.imageFile = file;
        this.imageUrl = URL.createObjectURL(file);
      }
    },
    async uploadAvatar() {
      if (!this.imageFile) return;
      
      const formData = new FormData();
      formData.append('file', this.imageFile);
      formData.append('userId', this.userId);
      
      try {
        const response = await axios.post('/api/upload/avatar', formData);
        // 前端仍使用avatarUrl作为变量名，但接收后端的avatar_url字段
        const avatarUrl = response.data.avatar_url;
        localStorage.setItem('avatarUrl', avatarUrl);
        this.$emit('upload-success', avatarUrl);
      } catch (error) {
        console.error('上传失败:', error);
      }
    }
  }
}
</script>