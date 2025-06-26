package com.example.cardcase.card.repository;

import com.example.cardcase.card.entity.BusinessCard;
import com.example.cardcase.card.entity.Relation;
import com.example.cardcase.oauth.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RelationRepository extends JpaRepository<Relation, Long> {
    List<Relation> findByMember(Member member);

    Optional<Relation> findByMemberAndBusinessCardId(Member member, Long businessCardId);

    boolean existsByMemberAndBusinessCard(Member member, BusinessCard businessCard);
}
