package com.new_dancing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().and()
                .authorizeRequests()
                .antMatchers("/api/**", "/music/**").permitAll()  // 确保 API 和 music 路径不受权限限制
                .anyRequest().authenticated();  // 其他请求需要认证
    }

    // 使用 CorsConfigurationSource 来配置 CORS 策略
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:5173");  // 前端地址
        configuration.addAllowedOrigin("http://localhost:8080");  // 后端地址
        configuration.addAllowedMethod("*");  // 允许所有 HTTP 方法
        configuration.addAllowedHeader("*");  // 允许所有请求头
        configuration.setAllowCredentials(true);  // 允许发送凭证
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);  // 对 API 路径应用 CORS 配置
        source.registerCorsConfiguration("/music/**", configuration);  // 对 music 路径应用 CORS 配置
        return source;
    }
}
