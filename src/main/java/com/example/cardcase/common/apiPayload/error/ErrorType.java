package com.example.cardcase.common.apiPayload.error;

import org.springframework.http.HttpStatus;

public interface ErrorType {

    String name();

    HttpStatus getStatus();

    String getMessage();
}
