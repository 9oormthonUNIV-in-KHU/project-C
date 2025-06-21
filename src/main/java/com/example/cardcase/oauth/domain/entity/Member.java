package com.example.cardcase.oauth.domain.entity;

import com.example.cardcase.card.entity.Relation;
import com.example.cardcase.oauth.domain.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.cdi.Eager;

import javax.smartcardio.Card;
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

    private String email;
    private String password;
    private String name;
    @OneToMany(mappedBy = "member" , cascade = CascadeType.ALL)
    private List<Relation> relations = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Role> roles = new ArrayList<>();

}
