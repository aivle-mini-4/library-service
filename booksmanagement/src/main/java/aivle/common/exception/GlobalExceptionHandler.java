package aivle.common.exception;

import aivle.common.response.ApiResponse;
import aivle.common.response.ResponseFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static aivle.common.response.ErrorCode.INTERNAL_SERVER_ERROR;

@RestControllerAdvice

@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ResponseFactory responseFactory;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        log.error("서버 예외 발생 : {}", e.toString());
        return responseFactory.error(new CustomException(INTERNAL_SERVER_ERROR, e.getMessage()));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException e) {
        log.error("커스텀 예외 발생 : {}", e.toString());
        return responseFactory.error(e);
    }
}



