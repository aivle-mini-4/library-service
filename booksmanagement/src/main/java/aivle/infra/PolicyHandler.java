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
    BooksRepository booksRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='ViewHistoryRegistered'"
    )
    public void wheneverViewHistoryRegistered_AssignBestsellerBadge(
        @Payload ViewHistoryRegistered viewHistoryRegistered
    ) {
        ViewHistoryRegistered event = viewHistoryRegistered;
        System.out.println(
            "\n\n##### listener AssignBestsellerBadge : " +
            viewHistoryRegistered +
            "\n\n"
        );

        // Comments //
        //Whenever 도서열람수 >= 5 Then 베스트셀러등록(배지 부여)

        // Sample Logic //
        Books.assignBestsellerBadge(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='ReadyToPublish'"
    )
    public void wheneverReadyToPublish_RegisterBook(
        @Payload ReadyToPublish readyToPublish
    ) {
        ReadyToPublish event = readyToPublish;
        System.out.println(
            "\n\n##### listener RegisterBook : " + readyToPublish + "\n\n"
        );

        // Sample Logic //
        Books.registerBook(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
