package com.example.cardcase.common.apiPayload.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GlobalErrorType implements ErrorType {

    E500(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 내부 오류입니다."),
    MEMBER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "Member Not Found."), // "존재하지 않는 사용자" 401 Unauthorized
    NOT_ALLOWED(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    CARD_NOT_FOUND(HttpStatus.NOT_FOUND, "카드를 찾을 수 없습니다."),
    ;

    private final HttpStatus status;

    private final String message;
}