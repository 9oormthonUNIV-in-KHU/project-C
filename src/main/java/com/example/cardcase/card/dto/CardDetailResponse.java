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
                .companyName(businessCard.getCompany_name())
                .department(businessCard.getDepartment())
                .phoneNumber(businessCard.getPhone_number())
                .email(businessCard.getEmail())
                .companyEmail(businessCard.getCompany_email())
                .companyLocation(businessCard.getCompany_location())
                .sns(businessCard.getSns())
                .companyNumber(businessCard.getCompany_number())
                .build();
    }
}
