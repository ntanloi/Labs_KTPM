package com.example.spring_boot_api_jwt_ad.service;

import com.example.spring_boot_api_jwt_ad.entity.RefreshToken;
import com.example.spring_boot_api_jwt_ad.entity.User;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(String token, User user);
    Optional<RefreshToken> findByToken(String token);
    RefreshToken verifyExpiration(RefreshToken token);
    void deleteByUser(User user);
    void revokeAllUserTokens(User user);
}