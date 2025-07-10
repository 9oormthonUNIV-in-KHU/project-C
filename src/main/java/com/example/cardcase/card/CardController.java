package com.example.cardcase.card;

import com.example.cardcase.card.dto.CardDetailResponse;
import com.example.cardcase.card.dto.CardSummaryResponse;
import com.example.cardcase.common.apiPayload.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cards")
public class CardController {
    private final CardService cardService;

    /**
     * 공유 받은 명함 목록 조회
     */
    @GetMapping
    public ApiResponse<List<CardSummaryResponse>> getMyCardList(
            @AuthenticationPrincipal User userPrincipal
    ) {
        String memberEmail = userPrincipal.getUsername();

        List<CardSummaryResponse> cardList = cardService.getCardList(memberEmail);
        return ApiResponse.success(cardList);
    }

    /**
     * 명함 상세 조회 API
     */
    @GetMapping("/{cardId}")
    public ApiResponse<CardDetailResponse> getCardDetail(
            @PathVariable Long cardId,
            @AuthenticationPrincipal User userPrincipal
    ) {
        String memberEmail = userPrincipal.getUsername();
        CardDetailResponse cardDetail = cardService.getCardDetail(memberEmail, cardId);
        return ApiResponse.success(cardDetail);
    }

    /**
     * 공유받은 명함 삭제 API
     */
    @DeleteMapping("/{cardId}/my-case")
    public ApiResponse<?> removeCardFromMyCase(
            @PathVariable Long cardId,
            @AuthenticationPrincipal User userPrincipal
    ) {
        String memberEmail = userPrincipal.getUsername();
        cardService.removeCardFromMyCase(memberEmail, cardId);
        return ApiResponse.success();
    }

    /**
     * 내 명함 삭제 API
     */
    @DeleteMapping("/{cardId}")
    public ApiResponse<?> deleteBusinessCard(
            @PathVariable Long cardId,
            @AuthenticationPrincipal User userPrincipal
    ) throws AccessDeniedException {
        String memberEmail = userPrincipal.getUsername();
        cardService.deleteBusinessCard(memberEmail, cardId);
        return ApiResponse.success();
    }
}
