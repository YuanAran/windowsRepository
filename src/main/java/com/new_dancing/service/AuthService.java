package com.new_dancing.service;

import com.new_dancing.model.User;
import org.springframework.stereotype.Service;
import com.new_dancing.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import java.util.Collections;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public User register(User user) {
        // 检查用户名是否已存在
        if(userRepository.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("用户名已存在");
        }
        
        // 密码加密处理
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        
        // 保存用户信息到数据库
        return userRepository.save(user);
    }
    
    public String login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        
        // 生成JWT token
        String token = Jwts.builder()
                .setSubject(username)
                .claim("id", user.getId())
                .claim("email", user.getEmail())
                .claim("username", user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24小时有效期
                .signWith(SignatureAlgorithm.HS512, "yourSecretKey")
                .compact();
                
        // 设置Spring Security认证上下文
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            username, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        return token;
    }

    
    public User getUserFromToken(String token) {
        try {
            // 解析JWT token
            String username = Jwts.parser()
                .setSigningKey("yourSecretKey")
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody()
                .getSubject();
                
            return userRepository.findByUsername(username);
        } catch (Exception e) {
            throw new RuntimeException("无效的token: " + e.getMessage());
        }
    }
}