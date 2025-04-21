
const API_BASE_URL = 'http://localhost:8080/api/friends';

const FriendService = {
  async getFriends() {
    try {
      const response = await fetch(`${API_BASE_URL}/list`, {
        credentials: 'include',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      });
      if (!response.ok) throw new Error('Network response was not ok');
      const data = await response.json();
      console.log('获取好友列表响应:', data);
      return data.data;
    } catch (error) {
      console.error('获取好友列表失败:', error);
      throw error;
    }
  },
  
  async getFriendRequests() {
    try {
      const response = await fetch(`${API_BASE_URL}/requests`, {
        credentials: 'include',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      });
      if (!response.ok) throw new Error('Network response was not ok');
      const data = await response.json();
      return data.data;
    } catch (error) {
      console.error('获取好友请求失败:', error);
      throw error;
    }
  },
  
  async searchUsers(userId) {
    try {
      const response = await fetch(`${API_BASE_URL}/search?id=${userId}`, {
        credentials: 'include'
      });
      console.log('搜索响应状态:', response.status);
      const data = await response.json();
      console.log('搜索响应数据:', data);
      if (!response.ok) throw new Error('Network response was not ok');
      return data;
    } catch (error) {
      console.error('搜索用户失败:', error);
      throw error;
    }
  },
  
  async sendFriendRequest(friendId) {
    try {
      const response = await fetch(`${API_BASE_URL}/request/${friendId}`, {
        method: 'POST',
        credentials: 'include',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      });
      if (!response.ok) throw new Error('Network response was not ok');
      const data = await response.json();
      return data.data;
    } catch (error) {
      console.error('发送好友请求失败:', error);
      throw error;
    }
  },
  
  async acceptFriendRequest(requestId) {
    try {
      const response = await fetch(`${API_BASE_URL}/accept/${requestId}`, {
        method: 'POST',
        credentials: 'include',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      });
      if (!response.ok) throw new Error('Network response was not ok');
      const data = await response.json();
      return data.data;
    } catch (error) {
      console.error('接受好友请求失败:', error);
      throw error;
    }
  }
};

export default FriendService;