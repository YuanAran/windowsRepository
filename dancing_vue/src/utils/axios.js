import axios from 'axios';
import { useUserStore } from '@/stores/user';

const instance = axios.create({
  baseURL: 'http://localhost:8080/api'
});

instance.interceptors.request.use((config) => {
  const userStore = useUserStore();
  if (userStore.token) {
    config.headers.Authorization = `Bearer ${userStore.token}`;
  }
  return config;
});

export default instance;