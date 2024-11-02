package com.morak.morak.common;

import com.morak.morak.common.error.ErrorCode;
import com.morak.morak.common.error.SuccessCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponseTemplate<T> {

    private static final String SUCCESS_STATUS = "success";
    private static final String FAIL_STATUS = "fail";
    private static final String ERROR_STATUS = "error";

    private String status;
    private String message;
    private T data;

    public static <T> ApiResponseTemplate<T> successResponse(T data, SuccessCode successCode) {
        return new ApiResponseTemplate<>(SUCCESS_STATUS, successCode.getMessage(), data);
    }

    public static <T> ApiResponseTemplate<T> successWithNoContent(SuccessCode successCode) {
        return new ApiResponseTemplate<>(SUCCESS_STATUS, successCode.getMessage(), null);
    }

    public static <T> ApiResponseTemplate<T> errorResponse(ErrorCode errorCode) {
        return new ApiResponseTemplate<>(ERROR_STATUS, errorCode.getMessage(), null);
    }

    public static <T> ApiResponseTemplate<T> errorResponse(ErrorCode errorCode, T message) {
        return new ApiResponseTemplate<>(ERROR_STATUS, errorCode.getMessage(), message);
    }

    private ApiResponseTemplate(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
