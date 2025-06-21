package com.example.cardcase.card.entity;

import com.example.cardcase.oauth.domain.entity.Member;
import jakarta.persistence.*;

@Entity
public class Relation {

    @Id
    @GeneratedValue
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")  // 유저에게 명함을 받은 것
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "given_bc_id")  // 받은 명함
    private BusinessCard businessCard;
}
