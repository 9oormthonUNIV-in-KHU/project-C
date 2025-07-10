package com.example.cardcase.oauth.handler;

import com.example.cardcase.common.security.JwtTokenProvider;
import com.example.cardcase.common.security.RefreshTokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService redisService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

          String name = oAuth2User.getAttribute("name");
        String authorities = oAuth2User.getAttribute("authorities");
        String email = oAuth2User.getAttribute("email");
        String accessToken = jwtTokenProvider.createAccessToken(name,email);
        String refreshToken = jwtTokenProvider.createRefreshToken(name);
        // 이름이랑 리프레시 토큰 넣어놓기
        redisService.saveRefreshToken(name, refreshToken);

        // 프론트엔드로 리디렉트
        response.sendRedirect("http://localhost:3000/oauth2/success?accessToken=" + accessToken + "&refreshToken=" + refreshToken);
    }
}
