package com.example.cardcase.card.dto;

import com.example.cardcase.card.entity.BusinessCard;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public record CardDetailResponse (
    Long cardId,
    String name,
    String companyName,
    String department,
    String phoneNumber,
    String email,
    String companyEmail,
    String companyLocation,
    String sns,
    String companyNumber
) {
    public static CardDetailResponse from(BusinessCard businessCard) {
        return CardDetailResponse.builder()
                .cardId(businessCard.getId())
                .name(businessCard.getName())
                .companyName(businessCard.getCompanyName())
                .department(businessCard.getDepartment())
                .phoneNumber(businessCard.getPhoneNumber())
                .email(businessCard.getEmail())
                .companyEmail(businessCard.getCompanyEmail())
                .companyLocation(businessCard.getCompanyLocation())
                .sns(businessCard.getSns())
                .companyNumber(businessCard.getCompanyNumber())
                .build();
    }
}
