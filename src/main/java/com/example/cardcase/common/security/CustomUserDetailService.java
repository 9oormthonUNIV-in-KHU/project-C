package com.example.cardcase.common.security;


import com.example.cardcase.common.apiPayload.error.CoreException;
import com.example.cardcase.common.apiPayload.error.GlobalErrorType;
import com.example.cardcase.oauth.domain.entity.Member;
import com.example.cardcase.oauth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByEmail(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new CoreException(GlobalErrorType.MEMBER_NOT_FOUND));
    }


    private UserDetails createUserDetails(Member member) {
        // CustomUser
       return new CustomUserDetails(member);
}
