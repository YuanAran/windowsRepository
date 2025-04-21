package com.new_dancing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")  // 允许对所有 API 请求配置跨域
                        .allowedOrigins("http://localhost:5173") // 允许前端域名
                        .allowedOrigins("http://10.12.51.22:5173")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的 HTTP 方法
                        .allowedHeaders("*") // 允许的请求头
                        .allowCredentials(true) // 允许发送 cookie
                        .maxAge(3600); // 预检请求缓存时间，单位秒

                // 为 /music/** 路径也添加跨域支持
                registry.addMapping("/music/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedOrigins("http://10.12.51.22:5173")// 允许前端域名
                        .allowedMethods("GET") // 允许的 HTTP 方法，GET 方法用于音频文件请求
                        .allowedHeaders("*") // 允许的请求头
                        .allowCredentials(true) // 允许发送 cookie
                        .maxAge(3600); // 预检请求缓存时间，单位秒
            }
        };
    }
}
