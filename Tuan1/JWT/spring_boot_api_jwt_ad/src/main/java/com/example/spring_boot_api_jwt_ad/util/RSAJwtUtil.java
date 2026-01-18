package com.example.spring_boot_api_jwt_ad.util;

import com.example.spring_boot_api_jwt_ad.authen.UserPrincipal;
import com.example.spring_boot_api_jwt_ad.config.RSAKeyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Component
public class RSAJwtUtil {
    
    @Autowired
    private JwtEncoder jwtEncoder;
    
    @Autowired
    private JwtDecoder jwtDecoder;
    
    // Access Token: 15 phút
    private static final long ACCESS_TOKEN_VALIDITY = 15;
    
    // Refresh Token: 7 ngày
    private static final long REFRESH_TOKEN_VALIDITY = 7 * 24 * 60;
    
    /**
     * Tạo Access Token với thời gian sống ngắn
     */
    public String generateAccessToken(UserPrincipal userPrincipal) {
        Instant now = Instant.now();
        
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("spring-boot-jwt-app")
                .issuedAt(now)
                .expiresAt(now.plus(ACCESS_TOKEN_VALIDITY, ChronoUnit.MINUTES))
                .subject(userPrincipal.getUsername())
                .claim("userId", userPrincipal.getUserId())
                .claim("authorities", userPrincipal.getAuthorities())
                .claim("tokenType", "access")
                .build();
                
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
    
    /**
     * Tạo Refresh Token với thời gian sống dài
     */
    public String generateRefreshToken(UserPrincipal userPrincipal) {
        Instant now = Instant.now();
        
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("spring-boot-jwt-app")
                .issuedAt(now)
                .expiresAt(now.plus(REFRESH_TOKEN_VALIDITY, ChronoUnit.MINUTES))
                .subject(userPrincipal.getUsername())
                .claim("userId", userPrincipal.getUserId())
                .claim("tokenType", "refresh")
                .id(UUID.randomUUID().toString())
                .build();
                
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
    
    /**
     * Xác thực và giải mã token
     */
    public Jwt validateToken(String token) {
        try {
            return jwtDecoder.decode(token);
        } catch (JwtException e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }
    
    /**
     * Lấy username từ token
     */
    public String getUsernameFromToken(String token) {
        Jwt jwt = validateToken(token);
        return jwt.getSubject();
    }
    
    /**
     * Lấy userId từ token
     */
    public Long getUserIdFromToken(String token) {
        Jwt jwt = validateToken(token);
        return jwt.getClaim("userId");
    }
    
    /**
     * Kiểm tra token có hết hạn không
     */
    public boolean isTokenExpired(String token) {
        try {
            Jwt jwt = validateToken(token);
            return jwt.getExpiresAt().isBefore(Instant.now());
        } catch (Exception e) {
            return true;
        }
    }
    
    /**
     * Lấy thời gian hết hạn của token
     */
    public Instant getExpirationFromToken(String token) {
        Jwt jwt = validateToken(token);
        return jwt.getExpiresAt();
    }
}