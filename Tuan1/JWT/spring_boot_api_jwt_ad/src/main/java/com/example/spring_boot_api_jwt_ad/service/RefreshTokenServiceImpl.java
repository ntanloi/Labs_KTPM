package com.example.spring_boot_api_jwt_ad.service;

import com.example.spring_boot_api_jwt_ad.entity.RefreshToken;
import com.example.spring_boot_api_jwt_ad.entity.User;
import com.example.spring_boot_api_jwt_ad.repository.RefreshTokenRepository;
import com.example.spring_boot_api_jwt_ad.util.RSAJwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {
    
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    
    @Autowired
    private RSAJwtUtil rsaJwtUtil;
    
    @Override
    public RefreshToken createRefreshToken(String token, User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(rsaJwtUtil.getExpirationFromToken(token));
        refreshToken.setRevoked(false);
        
        return refreshTokenRepository.save(refreshToken);
    }
    
    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }
    
    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now()) || token.isRevoked()) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token was expired or revoked. Please make a new signin request");
        }
        return token;
    }
    
    @Override
    public void deleteByUser(User user) {
        refreshTokenRepository.deleteByUser(user);
    }
    
    @Override
    public void revokeAllUserTokens(User user) {
        refreshTokenRepository.revokeAllUserTokens(user);
    }
}