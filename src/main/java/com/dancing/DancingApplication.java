package com.dancing;

import com.dancing.netty.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.dancing")
public class DancingApplication {

    public static void main(String[] args) {
        SpringApplication.run(DancingApplication.class, args);
        
        // 启动 Netty 服务器
        try {
            new NettyServer(8081).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}