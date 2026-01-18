package com.example.spring_boot_api_jwt_ad.dto;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
}