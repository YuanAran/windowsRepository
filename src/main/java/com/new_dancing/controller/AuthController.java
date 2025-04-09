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
    public User register(@RequestBody User user) {
        return authService.register(user);
    }
    
    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        String token = authService.login(credentials.get("username"), credentials.get("password"));
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
}