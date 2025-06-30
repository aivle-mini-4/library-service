package aivle.infrastructure.adapter.in;

import aivle.application.port.out.BookViewCommandPort;
import aivle.common.exception.CustomException;
import aivle.domain.event.BookRegistered;
import aivle.infrastructure.mapper.DomainToProjectionMapper;
import aivle.infrastructure.messaging.KafkaProcessor;
import aivle.infrastructure.projection.BookView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static aivle.common.response.ErrorCode.INTERNAL_SERVER_ERROR;

@Component

@RequiredArgsConstructor
@Slf4j
public class BookReadEventListener {

    private final BookViewCommandPort viewCommandPort;
    private final DomainToProjectionMapper domainToProjectionMapper;

    @Transactional
    @StreamListener(value = KafkaProcessor.INPUT, condition = "headers['type']=='BookRegistered'")
    public void handleBookRegistered(@Payload BookRegistered bookRegistered) {
        log.debug("Received BookRegistered event: {}", bookRegistered);
        try {
            if (!bookRegistered.validate()) return;
            BookView bookView = domainToProjectionMapper.toBookView(bookRegistered);
            viewCommandPort.save(bookView);
        } catch (Exception e) {
            throw new CustomException(INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

//    @StreamListener(KafkaProcessor.INPUT)
//    public void whenBookDeleted_then_DELETE_1(
//            @Payload BookDeleted bookDeleted
//    ) {
//        try {
//            if (!bookDeleted.validate()) return;
//            // view 레파지 토리에 삭제 쿼리
//            bookViewRepository.deleteById(bookDeleted.getId());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
