package com.example.cardcase.oauth.user;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo {
private Map<String, Object> attributes;

    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProvider(){
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }
    // account_email이 사업자 등록증이 있어야해서 그냥 이메일은 안 받는거로
    @Override
    public String getProviderEmail() {
       return "kakao";
    }

    @Override
    public String getProviderName() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        return (String) profile.get("nickname");
    }
}
