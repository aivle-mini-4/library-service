package aivle.infra;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "openai", url = "${openai.url}")
public interface OpenAIClient {
    
    @PostMapping(value = "/images/generations")
    ImageResponse generateImage(
        @RequestHeader("Authorization") String auth,
        @RequestBody ImageRequest request
    );

    @PostMapping(value = "/chat/completions")
    ChatCompletionResponse createCompletion(
        @RequestHeader("Authorization") String auth,
        @RequestBody ChatCompletionRequest request
    );
}
