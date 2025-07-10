package com.example.cardcase.oauth.domain.entity;

import com.example.cardcase.card.entity.BusinessCard;
import com.example.cardcase.card.entity.Relation;
import com.example.cardcase.oauth.domain.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "member") // 테이블명 멤버로
public class Member {
    @Id
    @GeneratedValue
    private Long id;

// 소셜 로그인을 위한 ID
    @Column(unique = true)
    private String socialId;

    @Column(unique = true)
    private String provider; // 카카오 구글 네이버 중 하나
    private String providerId;
    private String email;
    private String password;
    private String name;
    @OneToMany(mappedBy = "member" , cascade = CascadeType.ALL)
    private List<Relation> relations = new ArrayList<>();

    @OneToMany(mappedBy = "member" , cascade = CascadeType.ALL)
    private List<BusinessCard> businessCards = new ArrayList<>();


    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Role> roles = new ArrayList<>();

}
