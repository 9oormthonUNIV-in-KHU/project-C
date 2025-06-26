package com.example.cardcase.card.entity;

import com.example.cardcase.oauth.domain.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Relation {

    @Builder
    public Relation(Member member, BusinessCard businessCard) {
        this.member = member;
        this.businessCard = businessCard;
    }

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")  // 유저에게 명함을 받은 것
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "given_bc_id")  // 받은 명함
    private BusinessCard businessCard;
}
