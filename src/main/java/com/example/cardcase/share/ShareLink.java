package com.example.cardcase.share;

import com.example.cardcase.card.entity.BusinessCard;
import com.example.cardcase.oauth.domain.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "share_link") // DB 테이블명을 명시적으로 지정합니다.
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA는 기본 생성자를 필요로 합니다. 무분별한 생성을 막기 위해 protected로 설정합니다.
@EntityListeners(AuditingEntityListener.class) // createdAt 자동 생성을 위해 추가합니다.
public class ShareLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "share_code", nullable = false, unique = true, length = 36)
    private String shareCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_card_id", nullable = false)
    private BusinessCard businessCard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_user_id", nullable = false)
    private Member creator;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    @CreatedDate // 엔티티 생성 시 자동으로 현재 시간이 기록됩니다.
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt; // 링크 생성 시간

    @Builder
    public static ShareLink create(BusinessCard businessCard, Member creator, LocalDateTime expiredAt) {
        ShareLink shareLink = new ShareLink();
        shareLink.shareCode = UUID.randomUUID().toString(); // 고유 코드 생성
        shareLink.businessCard = businessCard;
        shareLink.creator = creator;
        shareLink.expiredAt = expiredAt;
        return shareLink;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiredAt);
    }
}
