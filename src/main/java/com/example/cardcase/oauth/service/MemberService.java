package com.example.cardcase.oauth.service;

import com.example.cardcase.common.security.JwtToken;
import com.example.cardcase.common.security.JwtTokenProvider;
import com.example.cardcase.oauth.domain.dto.SignUpRequest;
import com.example.cardcase.oauth.domain.dto.SignUpResponse;
import com.example.cardcase.oauth.domain.entity.Member;
import com.example.cardcase.oauth.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Transactional
@Service
@RequiredArgsConstructor
public class MemberService {

private final MemberRepository memberRepository;
private final PasswordEncoder passwordEncoder;
private final JwtTokenProvider jwtTokenProvider;
private final AuthenticationManager authenticationManager;


public JwtToken login(String email, String password){
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
    Authentication authentication = authenticationManager.authenticate(authenticationToken);
    return jwtTokenProvider.generateToken(authentication);
}

public SignUpResponse signUp(SignUpRequest signUpRequest) {
    if(memberRepository.existsByEmail(signUpRequest.email())){
        throw new IllegalArgumentException("이미 사용중인 이메일");
    }
    String encodedPassword = passwordEncoder.encode(signUpRequest.password());
    Member member = memberRepository.save(signUpRequest.toMember(encodedPassword));
    return SignUpResponse.of(member);
}
}
