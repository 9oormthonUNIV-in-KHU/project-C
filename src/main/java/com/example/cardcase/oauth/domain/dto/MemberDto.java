package com.example.cardcase.oauth.domain.dto;

import lombok.Getter;

@Getter
public class MemberDto {
    private Long id;
    private String email;
    private String name;

    public MemberDto(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }
}
