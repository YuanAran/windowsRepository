const ws = new WebSocket('wss://10.12.51.22:5555/ws');
ws.onerror = (event) => {
    console.error('WebSocket 连接错误:', event);
    // 打印错误详细信息
    console.log('错误类型:', event.type);
    console.log('错误时间戳:', event.timeStamp);
};
