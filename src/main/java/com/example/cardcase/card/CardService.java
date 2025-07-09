package com.example.cardcase.card;

import com.example.cardcase.card.dto.CardDetailResponse;
import com.example.cardcase.card.dto.CardSummaryResponse;
import com.example.cardcase.card.entity.BusinessCard;
import com.example.cardcase.card.entity.Relation;
import com.example.cardcase.card.repository.BusinessCardRepository;
import com.example.cardcase.card.repository.RelationRepository;
import com.example.cardcase.common.apiPayload.error.CoreException;
import com.example.cardcase.common.apiPayload.error.GlobalErrorType;
import com.example.cardcase.oauth.domain.entity.Member;
import com.example.cardcase.oauth.repository.MemberRepository; // Member 조회를 위해 필요
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardService {

    private final BusinessCardRepository businessCardRepository;
    private final RelationRepository relationRepository;
    //TODO : memberService를 이용해서 조회하는 것 고려. 어디에 의존할것인가
    private final MemberRepository memberRepository;

    /**
     * 공유받은 명함 상세 조회
     */
    public List<CardSummaryResponse> getCardList(String memberEmail) {
        Member member = findMemberByEmail(memberEmail);
        List<Relation> relations = relationRepository.findByMember(member);

        return relations.stream()
                .map(relation -> CardSummaryResponse.from(relation.getBusinessCard()))
                .collect(Collectors.toList());
    }

    /**
     * 명함 상세 조회
     */
    public CardDetailResponse getCardDetail(String memberEmail, Long cardId) {
        Member member = findMemberByEmail(memberEmail);
        BusinessCard businessCard = findBusinessCardById(cardId);

        if (businessCard.getMember().equals(member)) {
            return CardDetailResponse.from(businessCard);
        }

        relationRepository.findByMemberAndBusinessCardId(member, cardId)
                .orElseThrow(() -> new CoreException(GlobalErrorType.NOT_ALLOWED));


        return CardDetailResponse.from(businessCard);
    }

    /**
     * 공유받은 명함 삭제 (Relation 삭제)
     */
    @Transactional
    public void removeCardFromMyCase(String memberEmail, Long cardId) {
        Member member = findMemberByEmail(memberEmail);

        Relation relation = relationRepository.findByMemberAndBusinessCardId(member, cardId)
                .orElseThrow(() -> new CoreException(GlobalErrorType.CARD_NOT_FOUND));

        relationRepository.delete(relation);
    }

    /**
     * 명함 원본 삭제
     */
    @Transactional
    public void deleteBusinessCard(String memberEmail, Long cardId) {
        BusinessCard businessCard = findBusinessCardById(cardId);
        Member member = findMemberByEmail(memberEmail);
        if (businessCard.getMember().equals(member)) {
            relationRepository.deleteByBusinessCard(businessCard);

            //TODO: 추후에는 실제 삭제하고 백업서버에 원본 저장하기.
            businessCard.disown();
            //businessCardRepository.delete(businessCard);
        }
        else {
            throw new CoreException(GlobalErrorType.NOT_ALLOWED);
        }

    }

    private Member findMemberByEmail(String memberEmail) {
        return memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new CoreException(GlobalErrorType.MEMBER_NOT_FOUND));
    }

    private BusinessCard findBusinessCardById(Long cardId) {
        return businessCardRepository.findById(cardId)
                .orElseThrow(() -> new CoreException(GlobalErrorType.CARD_NOT_FOUND));
    }
}
