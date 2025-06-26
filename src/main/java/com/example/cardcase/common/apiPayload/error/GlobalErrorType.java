package com.example.cardcase.common.apiPayload.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GlobalErrorType implements ErrorType {

    E500(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 내부 오류입니다."),
    MEMBER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "Member Not Found."), // "존재하지 않는 사용자" 401 Unauthorized
    CARD_NOT_FOUND(HttpStatus.NOT_FOUND, "Card Not Found."),
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "공유 권한이 없습니다."),
    LINK_NOT_FOUND(HttpStatus.NOT_FOUND, "Link Not Found."),
    LINK_EXPIRED(HttpStatus.BAD_REQUEST, "만료된 링크입니다."),
    CANNOT_ADD_OWN_CARD(HttpStatus.BAD_REQUEST, "자신의 명함은 추가할 수 없습니다."),
    CARD_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 공유받은 명함입니다.")
    ;

    private final HttpStatus status;

    private final String message;
}