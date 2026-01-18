package com.example.spring_boot_api_jwt_ad.controller;

import com.example.spring_boot_api_jwt_ad.authen.UserPrincipal;
import com.example.spring_boot_api_jwt_ad.dto.RefreshTokenRequest;
import com.example.spring_boot_api_jwt_ad.dto.TokenResponse;
import com.example.spring_boot_api_jwt_ad.entity.RefreshToken;
import com.example.spring_boot_api_jwt_ad.entity.User;
import com.example.spring_boot_api_jwt_ad.service.RefreshTokenService;
import com.example.spring_boot_api_jwt_ad.service.UserService;
import com.example.spring_boot_api_jwt_ad.util.RSAJwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthControllerRSA {

    @Autowired
    private UserService userService;

    @Autowired
    private RSAJwtUtil rsaJwtUtil;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userService.createUser(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            UserPrincipal userPrincipal = userService.findByUsername(user.getUsername());

            if (userPrincipal == null || !passwordEncoder.matches(user.getPassword(), userPrincipal.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid username or password");
            }

            // Tạo Access Token và Refresh Token
            String accessToken = rsaJwtUtil.generateAccessToken(userPrincipal);
            String refreshToken = rsaJwtUtil.generateRefreshToken(userPrincipal);

            // Lưu Refresh Token vào database
            User userEntity = new User();
            userEntity.setId(userPrincipal.getUserId());
            refreshTokenService.createRefreshToken(refreshToken, userEntity);

            // Tạo response
            TokenResponse tokenResponse = new TokenResponse();
            tokenResponse.setAccessToken(accessToken);
            tokenResponse.setRefreshToken(refreshToken);
            tokenResponse.setTokenType("Bearer");
            tokenResponse.setExpiresIn(15 * 60); // 15 phút
            tokenResponse.setScope("read write");

            return ResponseEntity.ok(tokenResponse);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Login failed: " + e.getMessage());
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        try {
            String requestRefreshToken = request.getRefreshToken();

            // Tìm refresh token trong database
            RefreshToken refreshToken = refreshTokenService.findByToken(requestRefreshToken)
                    .orElseThrow(() -> new RuntimeException("Refresh token not found"));

            // Verify expiration
            refreshToken = refreshTokenService.verifyExpiration(refreshToken);

            // Lấy user info
            UserPrincipal userPrincipal = userService.findByUsername(refreshToken.getUser().getUsername());

            // Tạo access token mới
            String newAccessToken = rsaJwtUtil.generateAccessToken(userPrincipal);

            TokenResponse tokenResponse = new TokenResponse();
            tokenResponse.setAccessToken(newAccessToken);
            tokenResponse.setRefreshToken(requestRefreshToken);
            tokenResponse.setTokenType("Bearer");
            tokenResponse.setExpiresIn(15 * 60); // 15 phút
            tokenResponse.setScope("read write");

            return ResponseEntity.ok(tokenResponse);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Refresh token failed: " + e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                String username = rsaJwtUtil.getUsernameFromToken(token);
                
                UserPrincipal userPrincipal = userService.findByUsername(username);
                User user = new User();
                user.setId(userPrincipal.getUserId());
                
                // Revoke all refresh tokens for this user
                refreshTokenService.revokeAllUserTokens(user);
                
                return ResponseEntity.ok("Logged out successfully");
            }
            return ResponseEntity.badRequest().body("Invalid authorization header");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Logout failed: " + e.getMessage());
        }
    }

    @GetMapping("/protected")
    @PreAuthorize("hasAuthority('USER_READ')")
    public ResponseEntity<?> protectedEndpoint() {
        return ResponseEntity.ok("This is a protected endpoint accessible with valid JWT token");
    }
}