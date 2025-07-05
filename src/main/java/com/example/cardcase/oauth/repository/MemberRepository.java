package com.example.cardcase.oauth.repository;

import com.example.cardcase.oauth.domain.dto.MemberDto;
import com.example.cardcase.oauth.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
   Optional<Member> findByEmail (String email);
   @Query("SELECT new com.example.cardcase.oauth.domain.dto.MemberDto(m.id, m.email, m.name) "
           + "FROM Member m")
   List<MemberDto> findAllMemberDtos();
   boolean existsByEmail(String email);
}
