package aivle.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import aivle.infra.ChatCompletionRequest;
import aivle.infra.ChatCompletionResponse;
import aivle.infra.ImageRequest;
import aivle.infra.ImageResponse;
import aivle.infra.OpenAIClient;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@Service
public class AiService {

    private static OpenAIClient openAIClient;
    private static String apiKey;

    public AiService(OpenAIClient openAIClient, @Value("${openai.api-key}") String apiKey) {
        AiService.openAIClient = openAIClient;
        AiService.apiKey = "Bearer " + apiKey;
    }


    public static void autoPublishWithAi(
        PublicationRequested publicationRequested
    ) {
        ProcessingResult result = processBook(publicationRequested.getTitle(), publicationRequested.getContent());

        ReadyToPublish readyToPublish = new ReadyToPublish(publicationRequested.getAuthorId(), publicationRequested.getTitle(), publicationRequested.getContent(), result);
        readyToPublish.publishAfterCommit();
    }

    private static ProcessingResult processBook(String title, String content) {
        // 1. 책 표지 이미지 생성
        ImageRequest imgReq = new ImageRequest();
        imgReq.setPrompt("다음 책 제목과 내용을 바탕으로 표지 아트워크를 만들어주세요. 배경 없이(책 프레임이나 책등, 책상, 펜 등 주변 소품 일체 없이) 포스터 형태로 표지만 출력해 주세요. 책 제목은 반드시 포함해주세요.\n\n책 제목: '" + title + "'\n" + "책 내용: " + content);
        ImageResponse imgRes = openAIClient.generateImage(apiKey, imgReq);
        String coverUrl = imgRes.getData()[0].getUrl();

        // 2. 책 내용 요약
        String summary = callChat("다음 책 내용을 요약하세요.\n책 내용: " + content);

        // 3. 카테고리 선정
        String categories = callChat("다음 책 제목과 내용을 바탕으로, 가장 적절한 하나의 카테고리(예: 소설, 에세이, 자기계발 등)만 한 단어로 응답하세요. 그 외 다른 설명은 하지 마십시오.\n\n책 제목: '" + title + "'\n" + "책 내용: " + content);

        // 4. 구독료 산정
        String price = callChat("다음 책 내용과 분량을 바탕으로 구독료를 제안하세요. 이 책에 적절한 구독료를 숫자(예: 250)로만 한 줄로 응답하세요. 그 외 다른 설명은 하지 마십시오. 구독료의 범위는 200포인트에서 500포인트 사이로, 10단위로 제안하세요.\n\n책 내용: " + content);

        return new ProcessingResult(coverUrl, summary, categories, Integer.parseInt(price));
    }

    private static String callChat(String prompt) {
        ChatCompletionRequest req = new ChatCompletionRequest();
        ChatCompletionRequest.Message msg = new ChatCompletionRequest.Message();
        msg.setRole("user");
        msg.setContent(prompt);
        req.setMessages(new ChatCompletionRequest.Message[]{ msg });

        ChatCompletionResponse res = openAIClient.createCompletion(apiKey, req);
        return res.getChoices()[0].getMessage().getContent();
    }

    @Data
    @AllArgsConstructor
    public static class ProcessingResult {
        private final String coverImageUrl;
        private final String summary;
        private final String categories;
        private final Integer estimatedPrice;
    }

}
