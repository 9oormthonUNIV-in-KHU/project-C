package com.example.cardcase.card.dto;

import com.example.cardcase.card.entity.BusinessCard;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CardDetailResponse {
    private Long cardId;
    private String name;
    private String companyName;
    private String department;
    private String phoneNumber;
    private String email;
    private String companyEmail;
    private String companyLocation;
    private String sns;
    private String companyNumber;

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
