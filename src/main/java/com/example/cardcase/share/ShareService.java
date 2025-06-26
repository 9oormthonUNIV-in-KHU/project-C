package com.example.cardcase.share;

import com.example.cardcase.card.entity.BusinessCard;
import com.example.cardcase.card.entity.Relation;
import com.example.cardcase.card.repository.BusinessCardRepository;
import com.example.cardcase.card.repository.RelationRepository;
import com.example.cardcase.common.apiPayload.error.CoreException;
import com.example.cardcase.common.apiPayload.error.GlobalErrorType;
import com.example.cardcase.oauth.domain.entity.Member;
import com.example.cardcase.oauth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ShareService {

    private final ShareLinkRepository shareLinkRepository;
    private final BusinessCardRepository cardRepository;
    private final MemberRepository userRepository; //service사용 여부 추후 고민
    private final RelationRepository relationRepository;

    //TODO: 현재 공유 링크 유효시간은 10분. *추후 24시간으로 수정필요
    private static final long SHARE_LINK_VALID_MINUTES = 10;

    @Transactional
    public String createShareLink(Long cardId, Long userId) {
        Member creator = userRepository.findById(userId)
                .orElseThrow(() -> new CoreException(GlobalErrorType.MEMBER_NOT_FOUND));

        BusinessCard businessCard = cardRepository.findById(cardId)
                .orElseThrow(() -> new CoreException(GlobalErrorType.CARD_NOT_FOUND));

        if (!businessCard.isOwner(creator)) {
            throw new CoreException(GlobalErrorType.FORBIDDEN_ACCESS); // 자신의 명함이 아니면 권한 없음
        }

        LocalDateTime expiredAt = LocalDateTime.now().plusMinutes(SHARE_LINK_VALID_MINUTES);
        ShareLink shareLink = ShareLink.create(businessCard, creator, expiredAt);
        shareLinkRepository.save(shareLink);

        //TODO env로 관리 필요.
        return "http://localhost:8080/share/" + shareLink.getShareCode();
    }

    @Transactional
    public void addCardFromShareLink(String shareCode, Long receiverId) {
        ShareLink shareLink = shareLinkRepository.findByShareCode(shareCode)
                .orElseThrow(() -> new CoreException(GlobalErrorType.LINK_NOT_FOUND));

        if (shareLink.isExpired()) {
            throw new CoreException(GlobalErrorType.LINK_EXPIRED);
        }

        Member receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new CoreException(GlobalErrorType.MEMBER_NOT_FOUND));
        BusinessCard cardToAdd = shareLink.getBusinessCard();

        if (cardToAdd.isOwner(receiver)) {
            throw new CoreException(GlobalErrorType.CANNOT_ADD_OWN_CARD);
        }

        if (relationRepository.existsByMemberAndBusinessCard(receiver, cardToAdd)) {
            throw new CoreException(GlobalErrorType.CARD_ALREADY_EXISTS);
        }

        Relation newRelation = Relation.builder()
                .member(receiver)
                .businessCard(cardToAdd)
                .build();
        relationRepository.save(newRelation);
    }
}