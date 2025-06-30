package aivle.common.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@JsonPropertyOrder({"isSuccess", "message", "result"})

@Getter
public class ApiResponse<T> {
    private final Boolean isSuccess;
    private final String message;
    private final T result;

    public ApiResponse(Boolean isSuccess, String message, T result) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.result = result;
    }

    public static <T> ApiResponse<T> of(SuccessCode code, T result) {
        return new ApiResponse<>(true, code.getMessage(), result);
    }

    public static <T> ApiResponse<T> of(ErrorCode code, T result) {
        return new ApiResponse<>(false, code.getMessage(), result);
    }

    public static <T> ApiResponse<T> of(boolean success, String message, T result) {
        return new ApiResponse<>(success, message, result);
    }
}
