package com.new_dancing.service;

import com.new_dancing.model.User;
import org.springframework.stereotype.Service;
import com.new_dancing.repository.UserRepository; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

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
            return null;
        }
        
        if(!passwordEncoder.matches(password, user.getPassword())) {
            return null;
        }
        
        // 这里应该生成并返回JWT token
        // 暂时返回一个模拟token
        return "mock-jwt-token";
    }
}