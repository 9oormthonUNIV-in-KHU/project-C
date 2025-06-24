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
import jakarta.persistence.EntityNotFoundException;
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
    public List<CardSummaryResponse> getCardList(Long memberId) {
        Member member = findMemberById(memberId);
        List<Relation> relations = relationRepository.findByMember(member);

        return relations.stream()
                .map(relation -> CardSummaryResponse.from(relation.getBusinessCard()))
                .collect(Collectors.toList());
    }

    /**
     * 명함 상세 조회
     */
    public CardDetailResponse getCardDetail(Long cardId) {
        BusinessCard businessCard = findBusinessCardById(cardId);
        return CardDetailResponse.from(businessCard);
    }

    /**
     * 공유받은 명함 삭제 (Relation 삭제)
     */
    @Transactional
    public void removeCardFromMyCase(Long memberId, Long cardId) {
        Member member = findMemberById(memberId);
        Relation relation = relationRepository.findByMemberAndBusinessCardId(member, cardId)
                .orElseThrow(() -> new EntityNotFoundException("해당 명함을 명함첩에서 찾을 수 없습니다."));

        relationRepository.delete(relation);
    }

    /**
     * 명함 원본 삭제 (BusinessCard 삭제)
     * TODO: 이 명함을 받은 모든 사람의 명함첩에서도 사라지게 됨.
     *       권한 검증 코드 넣기. (자신이 만든 명함만 삭제 가능하도록)
     *       삭제하기 보다는 user와의 연결을 끊는 방식으로 개선시키기
     */
    @Transactional
    public void deleteBusinessCard(Long memberId, Long cardId) throws AccessDeniedException {
        BusinessCard businessCard = findBusinessCardById(cardId);

        if(businessCard.getMember() == null){
            throw new CoreException(GlobalErrorType.E500);
        }

        businessCard.disown();
        //트랜잭션이 있으므로 리포지토리 저장은 자동으로 발생!
    }

    // --- 2번 이상 중복되는 코드 빼기 ---
    // TODO: 짧은 코드인데 함수로 빼는게 나은걸까?
    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CoreException(GlobalErrorType.MEMBER_NOT_FOUND));
    }

    private BusinessCard findBusinessCardById(Long cardId) {
        return businessCardRepository.findById(cardId)
                .orElseThrow(() -> new EntityNotFoundException("명함을 찾을 수 없습니다. ID: " + cardId));
    }
}
