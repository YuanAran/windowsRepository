import { WebSocketServer } from 'ws';
import fs from 'fs';
import * as http from "node:http";
import * as https from "node:https";

// SSL 配置
const options = {
    key: fs.readFileSync('./cert/localhost-key.pem'),
    cert: fs.readFileSync('./cert/localhost.pem'),
};

// 创建 HTTPS 服务器
const server = https.createServer(options);
// const server = http.createServer();



// 创建 WebSocket 服务器并配置 verifyClient 进行 Origin 校验
const wss = new WebSocketServer({
    server,

    verifyClient: (info, done) => {
        const allowedOrigin = 'https://10.12.51.22:5555';// 允许的前端域名
        // 验证 Origin 头部是否是允许的域名
        if (info.origin === allowedOrigin) {
            console.log(`Origin 验证通过，允许连接：${info.origin}`); // 日志记录验证通过
            done(true);  // 允许连接
        } else {
            console.log(`Origin 验证失败，拒绝连接：${info.origin}`); // 日志记录验证失败
            done(false, 403, 'Forbidden');  // 拒绝连接
        }
    },
});

// 存储客户端信息
let clients = new Map(); // 用来存储每个客户端的 WebSocket 连接和用户ID

// 设置 WebSocket 连接事件
wss.on('connection', (ws) => {
    console.log('客户端连接成功');

    let userId = null;

    ws.on('message', (message) => {
        console.log('收到消息:', message);
        const data = JSON.parse(message);

        userId = data.from._value;
        console.log('userId:', userId);

        // 保存用户和连接的对应关系
        clients.set(userId, ws);
        console.log(`用户 ${userId} 连接已保存到客户端映射中`);

        switch (data.type) {
            case 'offer':
                console.log(`发送 Offer 给目标用户 ${data.to}`);
                // 发送邀请（Offer）给目标用户
                const targetWs = clients.get(data.to);
                if (targetWs && targetWs.readyState === WebSocket.OPEN) {
                    targetWs.send(JSON.stringify({
                        type: 'offer',
                        from: data.from,
                        sdp: data.sdp,
                    }));
                    console.log(`Offer 发送成功给 ${data.to}`);
                } else {
                    console.log(`目标用户 ${data.to} 不在线或连接已关闭`);
                }
                break;

            case 'answer':
                console.log(`发送 Answer 给发起用户 ${data.to}`);
                // 发送答复（Answer）给发起者
                const inviterWs = clients.get(data.to);
                if (inviterWs && inviterWs.readyState === WebSocket.OPEN) {
                    inviterWs.send(JSON.stringify({
                        type: 'answer',
                        from: data.from,
                        sdp: data.sdp,
                    }));
                    console.log(`Answer 发送成功给 ${data.to}`);
                } else {
                    console.log(`发起用户 ${data.to} 不在线或连接已关闭`);
                }
                break;

            case 'iceCandidate':
                console.log(`转发 ICE Candidate 给目标用户 ${data.to}`);
                // 转发 ICE 候选给目标用户
                const candidateRecipient = clients.get(data.to);
                if (candidateRecipient && candidateRecipient.readyState === WebSocket.OPEN) {
                    candidateRecipient.send(JSON.stringify({
                        type: 'iceCandidate',
                        from: data.from,
                        candidate: data.candidate,
                    }));
                    console.log(`ICE Candidate 发送成功给 ${data.to}`);
                } else {
                    console.log(`目标用户 ${data.to} 不在线或连接已关闭`);
                }
                break;

            default:
                console.log('未知消息类型:', data.type);
                break;
        }
    });

    ws.on('close', () => {
        if (userId) {
            clients.delete(userId);
            console.log(`用户 ${userId} 断开连接并已从客户端映射中移除`);
        }
        console.log('客户端断开连接');
    });

    ws.on('error', (err) => {
        console.error('WebSocket 错误:', err);
    });
});


// 启动服务器
const port = 8083;
const host = '10.12.51.22';
server.listen(port, host, () => {
    console.log(`WebSocket 服务器启动，监听 https://${host}:${port}`); // 服务器启动日志
});
