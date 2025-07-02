package aivle.infrastructure.adapter.in.event;

import aivle.common.exception.CustomException;
import aivle.domain.event.BookDeleted;
import aivle.infrastructure.adapter.out.projection.repository.BestSellerViewRepository;
import aivle.infrastructure.adapter.out.projection.repository.BookViewRepository;
import aivle.infrastructure.messaging.KafkaProcessor;
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
public class BookDeletedEventListener {

    private final BookViewRepository bookViewRepository;
    private final BestSellerViewRepository bestSellerViewRepository;

    @Transactional
    @StreamListener(
            value = KafkaProcessor.INPUT,
            condition = "headers['type']=='BookDeleted'"
    )
    public void onBookDeleted(@Payload BookDeleted evt) {
        log.debug("Received BookDeleted event: {}", evt);
        try {
            if (!evt.validate()) return;
            bookViewRepository.deleteById(evt.getBookId());
            bestSellerViewRepository.deleteById(evt.getBookId());
        } catch (Exception e) {
            throw new CustomException(INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
