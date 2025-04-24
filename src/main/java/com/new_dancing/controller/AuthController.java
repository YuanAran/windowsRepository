package com.new_dancing.controller;

import com.new_dancing.model.User;
import com.new_dancing.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", methods = {RequestMethod.POST})
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/register")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Map<String, Object>> register(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        try {
            User registeredUser = authService.register(user);
            response.put("success", true);
            response.put("data", registeredUser);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "注册失败: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        Map<String, Object> response = new HashMap<>();
        try {
            String token = authService.login(credentials.get("username"), credentials.get("password"));
            response.put("success", true);
            response.put("token", token);
            response.put("username", credentials.get("username"));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "登录失败: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @GetMapping("/user-info")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String, Object>> getUserInfo(@RequestHeader("Authorization") String token) {
        Map<String, Object> response = new HashMap<>();
        try {
            User currentUser = authService.getUserFromToken(token);
            if (currentUser == null) {
                response.put("success", false);
                response.put("message", "无效的token或用户不存在");
                return ResponseEntity.badRequest().body(response);
            }
            
            response.put("success", true);
            response.put("username", currentUser.getUsername());
            response.put("email", currentUser.getEmail());
            response.put("id", currentUser.getId().toString());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取用户信息失败: " + e.getMessage());
            response.put("details", "请检查请求头是否包含有效的Authorization令牌");
            return ResponseEntity.internalServerError().body(response);
        }
    }
}