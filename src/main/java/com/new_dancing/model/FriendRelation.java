package com.new_dancing.model;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
public class FriendRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "friend_id")
    private User friend;
    
    @Enumerated(EnumType.STRING)
    private FriendStatus status;
    
    public enum FriendStatus {
        PENDING, ACCEPTED, REJECTED
    }
}