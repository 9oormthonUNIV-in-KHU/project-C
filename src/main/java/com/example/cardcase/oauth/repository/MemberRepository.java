package com.example.cardcase.oauth.repository;

import com.example.cardcase.oauth.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
   Optional<Member> findByEmail (String email);
}
