package aivle.infrastructure.adapter.in;

import aivle.application.port.in.BookCommandUseCase;
import aivle.infrastructure.mapper.EventToCommandMapper;
import aivle.infrastructure.messaging.KafkaProcessor;
import aivle.integration.event.ReadyToPublish;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component

@RequiredArgsConstructor
@Slf4j
public class BookWriteEventListener {

    private final BookCommandUseCase writeUseCase;
    private final EventToCommandMapper eventMapper;

    @StreamListener(
            value = KafkaProcessor.INPUT,
            condition = "headers['type']=='ReadyToPublish'"
    )
    public void handleReadyToPublish(@Payload ReadyToPublish evt) {
        log.debug("Received ReadyToPublish event: {}", evt);
        writeUseCase.registerBook(eventMapper.toRegisterCommand(evt));
    }


//    @StreamListener(KafkaProcessor.INPUT)
//    public void whatever(@Payload String eventString) {
//    }
//
//    @StreamListener(
//            value = KafkaProcessor.INPUT,
//            condition = "headers['type']=='ViewHistoryRegistered'"
//    )
//    public void wheneverViewHistoryRegistered_AssignBestsellerBadge(
//            @Payload ViewHistoryRegistered viewHistoryRegistered
//    ) {
//        ViewHistoryRegistered event = viewHistoryRegistered;
//        System.out.println(
//                "\n\n##### listener AssignBestsellerBadge : " +
//                        viewHistoryRegistered +
//                        "\n\n"
//        );
//
//        // Comments //
//        //Whenever 도서열람수 >= 5 Then 베스트셀러등록(배지 부여)
//
//        // Sample Logic //
//        Book.assignBestsellerBadge(event);
//    }


}
