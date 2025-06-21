package com.example.cardcase.oauth.controller;

import com.example.cardcase.common.apiPayload.response.ApiResponse;
import com.example.cardcase.common.security.JwtToken;
import com.example.cardcase.oauth.domain.dto.LoginRequest;
import com.example.cardcase.oauth.domain.dto.SignUpRequest;
import com.example.cardcase.oauth.domain.dto.SignUpResponse;
import com.example.cardcase.oauth.service.AuthService;
import com.example.cardcase.oauth.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.cardcase.common.security.*;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MemberController {

    private final MemberService memberService;
    @PostMapping("/login")
    public ApiResponse<?> login(@Valid @RequestBody LoginRequest loginRequest) {
    try{
        JwtToken jwtToken = memberService.login(loginRequest.email(), loginRequest.password());
        return ApiResponse.success(jwtToken);
    }catch (Exception e){
        return ApiResponse.error("INVALID", "이메일이나 비밀번호가 일치하지 않습니다.");
    }
    }
@PostMapping("/signup")
    public ApiResponse<?> signup(@Valid @RequestBody SignUpRequest signUpRequest) {
        try{
            SignUpResponse signUpResponse = memberService.signUp(signUpRequest);
            return ApiResponse.success(signUpResponse);

        }catch (Exception e){
            return ApiResponse.error("INVALID", e.getMessage());
        }

}

}
