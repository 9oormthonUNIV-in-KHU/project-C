package com.example.cardcase.card;

import com.example.cardcase.card.dto.CardDetailResponse;
import com.example.cardcase.card.dto.CardSummaryResponse;
import com.example.cardcase.common.apiPayload.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cards")
public class CardController {
    private final CardService cardService;

    /**
     * 공유 받은 명함 목록 조회
     */
    @GetMapping
    public ApiResponse<List<CardSummaryResponse>> getMyCardList(
            // TODO: @AuthenticationPrincipal 로 로그인 사용자 정보 가져오기
            @RequestParam Long memberId // 임시 파라미터
    ) {
        List<CardSummaryResponse> cardList = cardService.getCardList(memberId);
        return ApiResponse.success(cardList);
    }

    /**
     * 공유 받은 명함 상세 조회 API
     */
    @GetMapping("/{cardId}")
    public ApiResponse<CardDetailResponse> getCardDetail(@PathVariable Long cardId) {
        CardDetailResponse cardDetail = cardService.getCardDetail(cardId);
        return ApiResponse.success(cardDetail);
    }

    /**
     * 공유받은 명함 삭제 API
     */
    @DeleteMapping("/{cardId}/my-case")
    public ApiResponse<?> removeCardFromMyCase(
            @PathVariable Long cardId,
            // TODO: @AuthenticationPrincipal 로 로그인 사용자 정보 가져오기
            @RequestParam Long memberId // 임시 파라미터
    ) {
        cardService.removeCardFromMyCase(memberId, cardId);
        return ApiResponse.success();
    }

    /**
     * 내 명함 삭제 API
     */
    @DeleteMapping("/{cardId}")
    public ApiResponse<?> deleteBusinessCard(
            @PathVariable Long cardId,
            // TODO: @AuthenticationPrincipal 로 로그인 사용자 정보 가져오기
            @RequestParam Long memberId // 임시 파라미터
    ) throws AccessDeniedException {
        // TODO: 삭제하더라도 공유받은 사람은 정보를 볼 수 있어야 한다??
        cardService.deleteBusinessCard(memberId, cardId);
        return ApiResponse.success();
    }
}
