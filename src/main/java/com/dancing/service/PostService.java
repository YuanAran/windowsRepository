package com.dancing.service;

import com.dancing.entity.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();
    Post createPost(String content, String username);
    Post likePost(Long postId, String username);
    Post dislikePost(Long postId, String username);
}