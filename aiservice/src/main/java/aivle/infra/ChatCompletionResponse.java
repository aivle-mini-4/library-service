package aivle.infra;

import lombok.Data;

@Data
public class ChatCompletionResponse {
    private Choice[] choices;

    @Data
    public static class Choice {
        private ChatCompletionRequest.Message message;
    }
}
