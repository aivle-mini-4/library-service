package aivle.global.response;

import aivle.global.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseFactory {

    public <T> ResponseEntity<ApiResponse<T>> success(SuccessCode code, T result) {
        return ResponseEntity.status(code.getHttpStatus())
                .body(ApiResponse.of(code, result));
    }

    public ResponseEntity<ApiResponse<Void>> success(SuccessCode code) {
        if (code == SuccessCode.NO_CONTENT) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(code.getHttpStatus())
                .body(ApiResponse.of(code, null));
    }

    public ResponseEntity<ApiResponse<Void>> error(CustomException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ApiResponse.of(e.getErrorCode(), null));
    }

    public <T> ResponseEntity<ApiResponse<T>> error(CustomException e, T result) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ApiResponse.of(e.getErrorCode(), result));
    }
}
