package com.new_dancing.repository;

import com.new_dancing.model.FriendRelation;
import com.new_dancing.model.FriendRelation.FriendStatus;
import com.new_dancing.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRelationRepository extends JpaRepository<FriendRelation, Long> {
    List<FriendRelation> findByUserAndStatus(User user, FriendStatus status);
    Optional<FriendRelation> findByUserId(Long user_id);
    List<FriendRelation> findByFriendAndStatus(User friend, FriendStatus status);
}