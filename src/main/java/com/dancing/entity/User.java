package com.dancing.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Override
    public boolean isAccountNonLocked() {
        return true; // 示例返回值，可根据实际情况修改
    }
    @Override
    public boolean isAccountNonExpired() {
        return true; // 示例返回值，可根据实际情况修改
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // 示例返回值，可根据实际情况修改
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 示例返回值，可根据实际情况修改
    }
    @Override
    public boolean isEnabled() {
        return true; // 示例返回值，可根据实际情况修改
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    private LocalDateTime createdAt;
    
    // 移除avatarUrl字段和相关方法
}