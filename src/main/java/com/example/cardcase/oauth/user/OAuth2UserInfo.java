package com.example.cardcase.oauth.user;

public interface OAuth2UserInfo {
    String getProviderId();
    String getProvider();
    String getProviderEmail();
    String getProviderName();
}

