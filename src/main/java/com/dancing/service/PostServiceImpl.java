package com.dancing.service;

import com.dancing.entity.*;
import com.dancing.repository.PostReactionRepository;
import com.dancing.repository.PostRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;



@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private PostReactionRepository reactionRepository;

    @Override
    @Transactional
    public Post likePost(Long postId, String username) {
        if (postId == null || username == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        return handleReaction(postId, username, PostReaction.ReactionType.LIKE);
    }

    @Override
    @Transactional
    public Post dislikePost(Long postId, String username) {
        if (postId == null || username == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        return handleReaction(postId, username, PostReaction.ReactionType.DISLIKE);
    }

    private Post handleReaction(Long postId, String username, PostReaction.ReactionType reactionType) {
        try {
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new RuntimeException("帖子不存在: " + postId));

            Optional<PostReaction> existingReaction = reactionRepository.findByPostAndUsername(post, username);
            
            if (existingReaction.isPresent()) {
                PostReaction reaction = existingReaction.get();
                if (reaction.isSameReactionType(reactionType)) {
                    reactionRepository.delete(reaction);
                } else {
                    reaction.updateReactionType(reactionType);
                    reactionRepository.save(reaction);
                }
            } else {
                createNewReaction(post, username, reactionType);
            }
            
            // 更新帖子反应计数
            Map<PostReaction.ReactionType, Long> counts = reactionRepository.countByPostGroupByReactionType(post);
            post.setLikes(counts.getOrDefault(PostReaction.ReactionType.LIKE, 0L).intValue());
            post.setDislikes(counts.getOrDefault(PostReaction.ReactionType.DISLIKE, 0L).intValue());
            
            return postRepository.save(post);
        } catch (Exception e) {
            throw new RuntimeException("处理点赞/点踩失败: " + e.getMessage(), e);
        }
    }

    private void createNewReaction(Post post, String username, PostReaction.ReactionType reactionType) {
        PostReaction newReaction = new PostReaction();
        newReaction.initialize(post, username, reactionType);
        reactionRepository.save(newReaction);
    }

    @Override
    public List<Post> getAllPosts() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllPosts'");
    }

    @Override
    @Transactional
    public Post createPost(String content, String username) {
        try {
            if (content == null || content.trim().isEmpty()) {
                throw new IllegalArgumentException("帖子内容不能为空");
            }
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("用户名不能为空");
            }

            Post post = new Post();
            post.setContent(content);
            post.setUsername(username);
            post.setLikes(0);
            post.setDislikes(0);
            
            return postRepository.save(post);
        } catch (Exception e) {
            throw new RuntimeException("创建帖子失败: " + e.getMessage(), e);
        }
    }
}