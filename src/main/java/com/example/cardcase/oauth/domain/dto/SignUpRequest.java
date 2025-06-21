package com.example.cardcase.oauth.domain.dto;


import com.example.cardcase.oauth.domain.Role;
import com.example.cardcase.oauth.domain.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.List;

public record SignUpRequest(
        @NotBlank
        @Schema(description = "이메일", example = "abc1234@khu.ac.kr")
        String email,

        @NotBlank
        @Schema(description = "비밀번호", example = "1234")
        String password,

        @NotBlank
        @Schema(description = "이름", example = "홍길동")
        String name
) {
    public Member toMember(String encodedPassword) {
        return Member.builder()
                .email(email)
                .password(encodedPassword)
                .name(name)
                .roles(List.of(Role.ROLE_USER))
                .build();
    }
}