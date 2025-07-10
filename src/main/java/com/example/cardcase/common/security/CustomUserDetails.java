package com.example.cardcase.common.security;


import com.example.cardcase.oauth.domain.entity.Member;
import com.example.cardcase.oauth.user.OAuth2UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails , OAuth2User {
    private final Member member;
    private Map<String,Object> attributes;
    // 일반 로그인
    public CustomUserDetails(Member member) {
        this.member = member;
    }
    //oauth 로그인
    public CustomUserDetails(Member member, Map<String,Object> attributes) {
        this.member = member;
        this.attributes = attributes;
    }
    @Override
    public String getName() {
        return member.getName(); // 또는 고유 식별값
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return member.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }


    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes(){
        return attributes;
    }


}
