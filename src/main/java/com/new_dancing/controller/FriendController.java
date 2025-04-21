package com.new_dancing.controller;

import com.new_dancing.model.FriendRelation;
import com.new_dancing.model.User;
import com.new_dancing.repository.FriendRelationRepository;
import com.new_dancing.repository.UserRepository;
import com.new_dancing.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/friends")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*")
public class FriendController {
    @Autowired
    private AuthService authService;
    
    @Autowired
    private FriendRelationRepository friendRelationRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @PostMapping("/request/{friendId}")
   //第二个对于查看好友申请的逻辑也有问题。因为对应好友数据库id是自增，需要将查询的id修改成user_id字段。这个与上面的错有关联，先修改上面的错
    public ResponseEntity<Map<String, Object>> sendFriendRequest(@RequestHeader("Authorization") String token, @PathVariable Long friendId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<User> friendOptional = userRepository.findById(friendId);
            if (!friendOptional.isPresent()) {
                response.put("success", false);
                response.put("message", "用户不存在");
                return ResponseEntity.badRequest().body(response);
            }
            FriendRelation relation = new FriendRelation();
            User user=authService.getUserFromToken(token);
            if(user==null){
                response.put("success", false);
                response.put("message", "用户不存在");
            }else{
                relation.setUser(user);
                relation.setFriend(friendOptional.get());
                relation.setStatus(FriendRelation.FriendStatus.PENDING);
                friendRelationRepository.save(relation);
                response.put("success", true);
                response.put("message", "好友请求已发送");
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "发送好友请求失败: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getFriends(@RequestHeader("Authorization") String token) {
        Map<String, Object> response = new HashMap<>();
        try {
            User user=authService.getUserFromToken(token);
            if (user == null){
                response.put("success", false);
                response.put("message", "用户不存在");
            }else{
                List<FriendRelation> friends = friendRelationRepository.findByUserAndStatus(user, FriendRelation.FriendStatus.ACCEPTED);
                response.put("success", true);
                response.put("data", friends);
                System.out.println("好友列表：" + friends);
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取好友列表失败: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchUser(@RequestParam Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<User> user = userRepository.findById(id);
            if (!user.isPresent()) {
                response.put("success", false);
                response.put("message", "未找到匹配用户");
                return ResponseEntity.ok(response);
            }
            response.put("success", true);
            response.put("data", user.get());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "搜索用户失败: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    @GetMapping("/requests")
    public ResponseEntity<Map<String, Object>> getFriendRequests(@RequestHeader("Authorization") String token) {
        Map<String, Object> response = new HashMap<>();
        try {
            User user = authService.getUserFromToken(token);
            if (user == null){
                response.put("success", false);
                response.put("message", "用户不存在");
            }else{
                List<FriendRelation> requests = friendRelationRepository.findByFriendAndStatus(user, FriendRelation.FriendStatus.PENDING);
                List<Map<String, Object>> requestData = new ArrayList<>();
                if(!requests.isEmpty()){
                    for (FriendRelation relation : requests) {
                            Map<String, Object> requestInfo = new HashMap<>();
                            requestInfo.put("id", relation.getId());
                            requestInfo.put("status", relation.getStatus());
                            requestInfo.put("user", relation.getUser());
                            requestData.add(requestInfo);
                    }
                    response.put("success", true);
                    response.put("data", requestData);
                }
                }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取好友请求失败: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    @PostMapping("/accept/{requestId}")
    public ResponseEntity<Map<String, Object>> acceptFriendRequest(@RequestHeader("Authorization") String token, @PathVariable Long requestId) {
        Map<String, Object> response = new HashMap<>();
        try {
            User user = authService.getUserFromToken(token);
            if (user == null){
                response.put("success", false);
                response.put("message", "用户不存在");
            }else{
                Optional<FriendRelation> relationOptional = friendRelationRepository.findByUserId(requestId);
                if (!relationOptional.isPresent()) {
                    response.put("success", false);
                    response.put("message", "请求不存在");
                    return ResponseEntity.badRequest().body(response);
                }

                FriendRelation relation = relationOptional.get();
                if (!relation.getFriend().equals(user)) {
                    response.put("success", false);
                    response.put("message", "无权限操作此请求");
                    return ResponseEntity.badRequest().body(response);
                }

                relation.setStatus(FriendRelation.FriendStatus.ACCEPTED);
                friendRelationRepository.save(relation);

                // 创建反向关系
                FriendRelation reverseRelation = new FriendRelation();
                reverseRelation.setUser(relation.getFriend());
                reverseRelation.setFriend(relation.getUser());
                reverseRelation.setStatus(FriendRelation.FriendStatus.ACCEPTED);
                friendRelationRepository.save(reverseRelation);

                response.put("success", true);
                response.put("message", "好友请求已接受");
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "接受好友请求失败: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}