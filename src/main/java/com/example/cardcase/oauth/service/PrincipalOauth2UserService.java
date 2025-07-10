package com.example.cardcase.oauth.service;


import com.example.cardcase.common.security.CustomUserDetails;
import com.example.cardcase.oauth.domain.Role;
import com.example.cardcase.oauth.domain.entity.Member;
import com.example.cardcase.oauth.repository.MemberRepository;
import com.example.cardcase.oauth.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public CustomUserDetails loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
       OAuth2User oAuth2User = super.loadUser(userRequest);

       // 회원 가입
        OAuth2UserInfo oAuth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }
        else if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")){
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        }
        else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            Map<String, Object> response = (Map<String, Object>) oAuth2User.getAttributes().get("response");
            oAuth2UserInfo = new NaverUserInfo(response);
        }
        else{
            System.out.println("지원하지 않음");

        }
        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getProviderEmail();
        String loginId = provider + "_" + providerId;
        String username = oAuth2UserInfo.getProviderName();
        String password = passwordEncoder.encode("겟엔데어");
    // TODO : ROLE에 대한 부분도 넣어줘야함

    Member member = memberRepository.findBySocialId(loginId).orElse(null);
    // 만약 멤버가 널이라면 새로 생성
    if(member == null){
        member = Member.builder()
                .socialId(loginId)
                .password(password)
                .email(email)
                .name(username)

                .provider(provider)
                .providerId(providerId)
                .roles(List.of(Role.ROLE_USER))
                .build();
             memberRepository.save(member);

    }
    else{
        System.out.println("이미 로그인을 한적이 있습니다.");
    }
    return new CustomUserDetails(member,oAuth2User.getAttributes());
    }


}