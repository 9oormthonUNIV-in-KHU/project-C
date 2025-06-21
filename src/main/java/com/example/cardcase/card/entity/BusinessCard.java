package com.example.cardcase.card.entity;

import com.example.cardcase.oauth.domain.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class BusinessCard {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String company_name;

    private String department;
    private String phone_number;
    private String email;
    private String company_email;
    private String company_location;
    private String sns;
    private String company_number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")  // 유저에게 명함을 받은 것
    private Member member;

    @OneToMany(mappedBy = "businessCard", cascade = CascadeType.ALL)
    private List<Relation> relations = new ArrayList<>();

    //사용자가 자신의 명함을 삭제 시키더라도 공유받은 명함의 정보가 유지되도록 연결만 끊는다.
    public void disown() {
        this.member = null;
    }
}
