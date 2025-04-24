<template>
  <div class="video-call-container">
    <h2>与好友一起视频学习吧</h2>

    <!-- 显示好友列表 -->
    <div>
      <h3>选择一个好友发起视频通话</h3>
      <ul>
        <li v-for="friend in friends" :key="friend.friend.id">
          <button @click="startVideoCall(friend.friend.id)">
            邀请：{{ friend.friend.username }}
          </button>
        </li>
        <li v-if="friends.length === 0">暂无好友</li>
      </ul>
    </div>

    <!-- 视频通话区域 -->
    <div class="video-container" v-if="isCalling">
      <div class="video-screen">
        <h4>我的视频</h4>
        <video ref="localVideo" autoplay muted></video>
      </div>
      <div class="video-screen">
        <h4>好友的视频</h4>
        <video ref="remoteVideo" autoplay></video>
      </div>
    </div>

    <!-- 禁用按钮，防止重复点击 -->
    <button v-if="!isCalling" :disabled="isCalling">开始视频通话</button>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';

const friends = ref([]);
const isCalling = ref(false);
const selectedFriendId = ref(null);
const localStream = ref(null);
const remoteStream = ref(null);
const peerConnection = ref(null);
const socket = ref(null);
const localVideo = ref(null);
const remoteVideo = ref(null);
const userId = ref(null);

// 获取好友列表
const fetchFriends = async () => {
  try {
    const response = await fetch('https://10.12.51.22:5555/api/friends/list', {
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
      },
    });
    const data = await response.json();
    friends.value = data.data || [];
    userId.value = data.data[0].user.id;
    console.log('好友列表获取成功:', friends.value);
    console.log('userId:', userId.value);
  } catch (error) {
    console.error('获取好友列表失败:', error);
    alert('无法获取好友列表');
  }
};

// 启动视频通话
const startVideoCall = async (friendId) => {
  try {
    isCalling.value = true;
    selectedFriendId.value = friendId;
    console.log(`准备开始视频通话，邀请好友 ID: ${friendId}`);

    // 获取本地媒体流
    localStream.value = await navigator.mediaDevices.getUserMedia({video: true, audio: true});
    console.log("本地流获取成功:", localStream.value);

    // 显示本地视频流
    if (localVideo.value && localStream.value) {
      localVideo.value.srcObject = localStream.value;
      console.log("本地视频流已绑定到 localVideo 元素");
    }

    // 创建 RTCPeerConnection
    peerConnection.value = new RTCPeerConnection({
      iceServers: [
        {urls: 'stun:stun.l.google.com:19302'},
        {urls: 'stun:stun1.l.google.com:19302'},
      ],
    });

    // 将本地流添加到 PeerConnection
    localStream.value.getTracks().forEach(track => {
      peerConnection.value.addTrack(track, localStream.value);
    });

    // 处理远程视频流
    peerConnection.ontrack = (event) => {
      console.log('远程流接收到的 event:', event);
      if (event.streams && event.streams[0]) {
        const remoteStream = event.streams[0];
        const videoTracks = remoteStream.getVideoTracks();
        if (videoTracks.length > 0) {
          console.log('远程流包含视频轨道:', videoTracks);
          if (remoteVideo.value) {
            remoteVideo.value.srcObject = remoteStream;
            remoteVideo.value.play().catch((error) => {
              console.error('播放远程视频时发生错误:', error);
            });
          }
        } else {
          console.error('远程流中没有视频轨道');
        }
      } else {
        console.error('没有有效的远程流');
      }
    };





    // 创建并发送 offer
    const offer = await peerConnection.value.createOffer();
    await peerConnection.value.setLocalDescription(offer);
    console.log("发送 offer:", offer);

    socket.value.send(JSON.stringify({type: 'offer', from: userId, to: selectedFriendId.value, sdp: offer}));
    console.log(`已发送视频通话邀请给好友 ${selectedFriendId.value}`);

    // 监听 ICE candidate
    peerConnection.value.onicecandidate = (event) => {
      if (event.candidate) {
        console.log("ICE Candidate:", event.candidate);
        socket.value.send(JSON.stringify({
          type: 'iceCandidate',
          from: userId,
          to: selectedFriendId.value,
          candidate: event.candidate,
        }));
      }
    };
  } catch (error) {
    console.error('获取媒体设备失败:', error);
    alert('获取媒体设备失败，请检查权限或设备连接');
  }
};

// 信令处理函数
const handleOffer = (offer) => {
  console.log("收到 offer:", offer);
  peerConnection.value.setRemoteDescription(new RTCSessionDescription(offer.sdp));
  peerConnection.value.createAnswer().then(answer => {
    peerConnection.value.setLocalDescription(answer);
    console.log("发送 answer:", answer);
    socket.value.send(JSON.stringify({type: 'answer', from: userId, to: offer.from, sdp: answer}));
  }).catch(err => {
    console.error("创建 answer 失败:", err);
  });
};

const handleAnswer = (answer) => {
  console.log("收到 answer:", answer);
  peerConnection.value.setRemoteDescription(new RTCSessionDescription(answer.sdp));
};

const handleCandidate = (candidate) => {
  console.log("收到 candidate:", candidate);
  peerConnection.value.addIceCandidate(new RTCIceCandidate(candidate)).catch((err) => {
    console.error("添加 ICE Candidate 失败:", err);
  });
};

// 初始化 WebSocket 客户端
onMounted(() => {
  socket.value = new WebSocket('wss://10.12.51.22:5555/ws');

  socket.value.onopen = () => {
    console.log('WebSocket 连接成功');
  };

  socket.value.onmessage = (event) => {
    const message = JSON.parse(event.data);
    console.log("接收到的信令消息:", message);
    switch (message.type) {
      case 'offer':
        handleOffer(message);
        break;
      case 'answer':
        handleAnswer(message);
        break;
      case 'candidate':
        handleCandidate(message);
        break;
      default:
        console.warn('未知类型的消息:', message);
    }
  };

  socket.value.onerror = (err) => {
    console.error('WebSocket 连接错误:', err);
  };

  socket.value.onclose = () => {
    console.log('WebSocket 连接关闭');
  };

  fetchFriends();
});
</script>

<style scoped>
.video-call-container {
  text-align: center;
}

.video-container {
  display: flex;
  justify-content: center;
  margin-top: 20px;
  gap: 20px;
}

.video-screen {
  text-align: center;
}

video {
  width: 100%;
  height: auto;
  border: 1px solid black;
}

button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}
</style>
