package com.example.cardcase.share;

import com.example.cardcase.common.apiPayload.response.ApiResponse;
import com.example.cardcase.common.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1") // 모든 엔드포인트에 공통적으로 적용될 상위 경로
public class ShareController {

    private final ShareService shareService;

    @PostMapping("/cards/{cardId}/share-links")
    public ResponseEntity<ApiResponse<String>> createShareLink(
            @PathVariable Long cardId,
            @AuthenticationPrincipal CustomUserDetails principalDetails) {

        Long userId = principalDetails.getId(); // 로그인한 사용자의 ID 획득
        String shareUrl = shareService.createShareLink(cardId, userId);

        return ResponseEntity.ok(ApiResponse.success(shareUrl));
    }

    @PostMapping("/share/{shareCode}")
    public ResponseEntity<ApiResponse<String>> addCardBySharing(
            @PathVariable String shareCode,
            @AuthenticationPrincipal CustomUserDetails principalDetails) {

        Long receiverId = principalDetails.getId();
        shareService.addCardFromShareLink(shareCode, receiverId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("명함이 성공적으로 추가되었습니다."));
    }
}
