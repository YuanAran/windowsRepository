package com.dancing.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "post_reactions")
public class PostReaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 必须保留的主键字段
    
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
    
    @Column(nullable = false)
    private String username; // 改为存储用户名
    
    @Enumerated(EnumType.STRING)
    @Column(name = "reaction_type", nullable = false)
    private ReactionType reactionType;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    public enum ReactionType {
        LIKE, DISLIKE
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    public boolean isSameReactionType(ReactionType typeToCheck) {
        return this.reactionType == typeToCheck;
    }

    public void updateReactionType(ReactionType newType) {
        this.reactionType = newType;
    }

    public void initialize(Post post, String username, ReactionType reactionType) {
        this.post = post;
        this.username = username;
        this.reactionType = reactionType;
    }
}