package com.home.waxing_home.global.error;

import com.home.waxing_home.global.common.FailResponse;
import com.home.waxing_home.global.error.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 전역 예외 처리

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ApiException 계층 예외 처리
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<FailResponse> handleApiException(ApiException ex) {
        log.error("API 예외 발생: {}", ex.getMessage());

        FailResponse failResponse = FailResponse.builder()
                .status(ex.getStatusCode())
                .message(ex.getMessage())
                .validation(ex.getValidation())
                .build();

        return ResponseEntity.status(ex.getStatusCode()).body(failResponse);
    }

    // 그 외 모든 예외 처리 (서버 오류)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<FailResponse> handleAllException(Exception ex) {
        log.error("알 수 없는 예외 발생: ", ex);

        FailResponse failResponse = FailResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("서버 오류가 발생했습니다.")
                .validation(null)
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(failResponse);
    }
}
