package com.dancing.controller;

import com.dancing.entity.Post;
import com.dancing.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<?> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("data", posts);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody Map<String, Object> request) {
        try {
            String content = request.get("content").toString();
            if(content == null || content.trim().isEmpty()) {
                throw new RuntimeException("帖子内容不能为空");
            }
            
            // 从当前认证信息中获取用户名
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            
            Post post = postService.createPost(content, username);
            return ResponseEntity.ok(createSuccessResponse(post));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createErrorResponse(
                e.getMessage() != null ? e.getMessage() : "服务器内部错误"
            ));
        }
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<?> likePost(@PathVariable Long postId) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Post post = postService.likePost(postId, username);
            return ResponseEntity.ok(createSuccessResponse(post));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("/{postId}/dislike")
    public ResponseEntity<?> dislikePost(@PathVariable Long postId) {
        try {
            // 从认证信息中获取用户名
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            
            Post post = postService.dislikePost(postId, username);
            return ResponseEntity.ok(createSuccessResponse(post));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
        }
    }



private Map<String, Object> createSuccessResponse(Object data) {
    Map<String, Object> response = new HashMap<>();
    response.put("status", 200);
    response.put("data", data);
    return response;
}

private Map<String, Object> createErrorResponse(String message) {
    Map<String, Object> response = new HashMap<>();
    response.put("status", 400);
    response.put("message", message);
    return response;
}
}