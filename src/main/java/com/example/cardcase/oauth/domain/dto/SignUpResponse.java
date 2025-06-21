package com.example.cardcase.oauth.domain.dto;


import com.example.cardcase.oauth.domain.Role;
import com.example.cardcase.oauth.domain.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record SignUpResponse(
        @Schema(description = "이메일", example = "abc1234@khu.ac.kr")
        String email,

        @Schema(description = "이름", example = "홍길동")
        String name,

        @Schema(description = "Role", example = "[\"ROLE_USER\", \"ROLE_ADMIN\"]")
        List<Role> roles

) {
    public static SignUpResponse of(Member member) {
        return new SignUpResponse(
                member.getEmail(),
                member.getName(),
                member.getRoles()
        );
    }
}