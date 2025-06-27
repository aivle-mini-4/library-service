package aivle.infra;

import aivle.config.kafka.KafkaProcessor;
import aivle.domain.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    AiServiceRepository aiServiceRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PublicationRequested'"
    )
    public void wheneverPublicationRequested_AutoPublishWithAi(
        @Payload PublicationRequested publicationRequested
    ) {
        PublicationRequested event = publicationRequested;
        System.out.println(
            "\n\n##### listener AutoPublishWithAi : " +
            publicationRequested +
            "\n\n"
        );

        // Comments //
        //- 표지 이미지 생성
        // - 전자책 요약
        // - 카테고리 생성
        // - 구독료 산정
        //

        // Sample Logic //
        AiService.autoPublishWithAi(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
