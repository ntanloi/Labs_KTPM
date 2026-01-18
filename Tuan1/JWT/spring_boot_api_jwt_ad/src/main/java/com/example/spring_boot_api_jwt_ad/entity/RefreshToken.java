package com.example.spring_boot_api_jwt_ad.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "t_refresh_token")
@Getter
@Setter
public class RefreshToken extends BaseEntity {
    
    @Column(nullable = false, unique = true)
    private String token;
    
    @Column(nullable = false)
    private Instant expiryDate;
    
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    
    @Column(nullable = false)
    private boolean revoked = false;
}