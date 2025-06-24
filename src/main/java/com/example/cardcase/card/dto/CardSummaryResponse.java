package com.example.cardcase.card.dto;

import com.example.cardcase.card.entity.BusinessCard;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public record CardSummaryResponse (
    Long cardId,
    String name,
    String companyName,
    String department
) {
    public static CardSummaryResponse from(BusinessCard businessCard) {
        return CardSummaryResponse.builder()
                .cardId(businessCard.getId())
                .name(businessCard.getName())
                .companyName(businessCard.getCompanyName())
                .department(businessCard.getDepartment())
                .build();
    }
}
