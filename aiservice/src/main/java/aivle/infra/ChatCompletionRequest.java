package aivle.infra;

import lombok.Data;

@Data
public class ChatCompletionRequest {
    private String model = "gpt-4o-mini";
    private Message[] messages;

    @Data
    public static class Message {
        private String role;
        private String content;
    }
}
