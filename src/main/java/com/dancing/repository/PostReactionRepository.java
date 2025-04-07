package com.dancing.repository;

import com.dancing.entity.Post;
import com.dancing.entity.PostReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Map;
import java.util.Optional;

public interface PostReactionRepository extends JpaRepository<PostReaction, Long> {
    Optional<PostReaction> findByPostAndUsername(Post post, String username);
    
    @Query("SELECT r.reactionType, COUNT(r) FROM PostReaction r WHERE r.post = :post GROUP BY r.reactionType")
    Map<PostReaction.ReactionType, Long> countByPostGroupByReactionType(@Param("post") Post post);
}