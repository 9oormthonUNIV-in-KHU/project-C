package com.example.cardcase.common.apiPayload.response;


import com.example.cardcase.common.apiPayload.error.ErrorMessage;
import com.example.cardcase.common.apiPayload.error.ErrorType;

public record ApiResponse<T>(ResultType result, T data, ErrorMessage error) {

    public static ApiResponse<?> success() {
        return new ApiResponse<>(ResultType.SUCCESS, null, null);
    }

    public static <S> ApiResponse<S> success(S data) {
        return new ApiResponse<>(ResultType.SUCCESS, data, null);
    }

    public static ApiResponse<?> error(ErrorType error) {
        return new ApiResponse<>(ResultType.ERROR, null, new ErrorMessage(error));
    }

    public static ApiResponse<?> error(String code, String message) {
        return new ApiResponse<>(ResultType.ERROR, null, new ErrorMessage(code, message));
    }

}
