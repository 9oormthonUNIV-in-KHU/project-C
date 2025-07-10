package com.example.cardcase.common.security;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${jwt.refresh-expiration-time}")
    private long refreshTokenExpirationTime;

    private String getKey(String name){
        return "refresh_token:" + name;
    }

    public void saveRefreshToken(String name, String refreshToken) {
        redisTemplate.opsForValue().set(
                getKey(name),
                refreshToken,
                refreshTokenExpirationTime,
                TimeUnit.MILLISECONDS
        );
    }

    public String getRefreshToken(String username) {
        return redisTemplate.opsForValue().get(getKey(username));
    }

    public void deleteRefreshToken(String username) {
        redisTemplate.delete(getKey(username));
    }
}