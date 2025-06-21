package com.example.cardcase.card.dto;

import com.example.cardcase.card.entity.BusinessCard;
import lombok.Builder;
import lombok.Getter;

// 목록 조회 시 보여줄 최소한의 정보
@Getter
@Builder
public class CardSummaryResponse {
    private Long cardId;
    private String name;
    private String companyName;
    private String department;

    public static CardSummaryResponse from(BusinessCard businessCard) {
        return CardSummaryResponse.builder()
                .cardId(businessCard.getId())
                .name(businessCard.getName())
                .companyName(businessCard.getCompany_name())
                .department(businessCard.getDepartment())
                .build();
    }
}
