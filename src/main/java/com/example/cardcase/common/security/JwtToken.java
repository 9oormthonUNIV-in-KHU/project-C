package com.example.cardcase.common.security;

public record JwtToken (
        String grantType,
        String accessToken,
        String refreshToken
){ }