package com.example.cardcase.oauth.service;

import com.example.cardcase.common.security.JwtToken;
import com.example.cardcase.common.security.JwtTokenProvider;
import com.example.cardcase.common.security.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class AuthService {

private final JwtTokenProvider jwtTokenProvider;
private final RefreshTokenService refreshTokenService;
private final RedisTemplate<String, String> redisTemplate;
private final UserDetailsService userDetailsService;
// refresh 토큰 기반으로 access 토큰을 재발급해주는 곳
public JwtToken reissue(String refreshToken){
    if(!jwtTokenProvider.validateToken(refreshToken)){
        throw new IllegalArgumentException("Invalid refresh token");
    }
    String username = jwtTokenProvider.getUsername(refreshToken);
    String storedRefreshToken = refreshTokenService.getRefreshToken(username);
    //redis 에 저장된 리프레시토큰과 같은지 확인하기
    if(storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)){
        throw new IllegalArgumentException("Invalid refresh token");
    }
    // 유저정보 기반으로 인증 객체 생성 (이미 인증된 유저니까 이제 비밀번호는 필요 없음)
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    return jwtTokenProvider.generateToken(authentication);
}
}
