import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080',
  withCredentials: true,  // 允许跨域携带凭证
  timeout: 5000
})

export default api