import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import fs from 'fs';
import path from 'path'

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: {
    https: {
      key: fs.readFileSync(path.join('E:', 'ssl', 'localhost-key.pem')),
      cert: fs.readFileSync(path.join('E:', 'ssl', 'localhost.pem')),
    },
    hmr: {
      protocol: 'wss',        // 使用 wss（WebSocket over SSL）
      host: '10.12.51.22',    // 设置为局域网中的 IP 地址
      port: 5173,             // 配置为开发服务器端口，通常是 5173
      clientPort: 5173,       // 确保与浏览器端口匹配
    },
    host: '0.0.0.0',
    port: 5173,
    allowedHosts: ['1067150ru78fm.vicp.fun', 'localhost', '0.0.0.0']
  }
})
