package com.example.cardcase.oauth.domain.entity;

import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private String name;
    private String email;
    private Map<String, Object> attributes;

    public static OAuthAttributes of(String registrationId, String userNameAttributeName,
                                     Map<String, Object> attributes) {
        if ("kakao".equals(registrationId)) {
            return ofKakao("id", attributes);
        }
        throw new RuntimeException("지원하지 않는 소셜 로그인입니다: " + registrationId);
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName,
                                           Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        return new OAuthAttributes(
                (String) profile.get("nickname"),
                (String) kakaoAccount.get("email"),
                attributes
        );
    }

    public OAuthAttributes(String name, String email, Map<String, Object> attributes) {
        this.name = name;
        this.email = email;
        this.attributes = attributes;
    }

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)

                .build();
    }
}

