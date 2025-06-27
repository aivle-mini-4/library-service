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
    ViewHistoryRepository viewHistoryRepository;

    @Autowired
    FavoriteRepository favoriteRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='월구독자도서열람됨'"
    )
    public void whenever월구독자도서열람됨_RegisterViewHistory(
        @Payload 월구독자도서열람됨 월구독자도서열람됨
    ) {
        월구독자도서열람됨 event = 월구독자도서열람됨;
        System.out.println(
            "\n\n##### listener RegisterViewHistory : " +
            월구독자도서열람됨 +
            "\n\n"
        );

        // Sample Logic //
        ViewHistory.registerViewHistory(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PointUsed'"
    )
    public void wheneverPointUsed_RegisterViewHistory(
        @Payload PointUsed pointUsed
    ) {
        PointUsed event = pointUsed;
        System.out.println(
            "\n\n##### listener RegisterViewHistory : " + pointUsed + "\n\n"
        );

        // Sample Logic //
        ViewHistory.registerViewHistory(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
