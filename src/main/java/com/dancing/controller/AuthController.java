package com.dancing.controller;

import com.dancing.entity.User;
import com.dancing.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            // 获取token和用户完整信息
            String username = credentials.get("username");
            String password = credentials.get("password");
            User token = authService.login(username, password);
            User user = authService.getUserByUsername(username);  // 新增获取用户信息
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", 200);
            response.put("message", "登录成功");
            
            Map<String, Object> data = new HashMap<>();
            data.put("username", username);
            data.put("token", token);
            data.put("expiresIn", 3600);
            // 添加用户信息
            // 检查User类是否有getAvatarUrl方法，如果没有则需要添加
            // 假设User类中有getAvatar()方法，这里进行替换
            data.put("email", user.getEmail());
            response.put("data", data);
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", 400);
            errorResponse.put("message", "用户名或密码错误"); // 模糊错误信息
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            // 直接使用传入的user对象，无需重复设置属性
            User registeredUser = authService.register(user);
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", 200);
            response.put("message", "注册成功");
            response.put("data", registeredUser);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", 400);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}