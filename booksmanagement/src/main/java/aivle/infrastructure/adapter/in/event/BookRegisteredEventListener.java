package aivle.infrastructure.adapter.in.event;

import aivle.common.exception.CustomException;
import aivle.domain.event.BookRegistered;
import aivle.infrastructure.mapper.EventToProjectionMapper;
import aivle.infrastructure.messaging.KafkaProcessor;
import aivle.infrastructure.adapter.out.projection.repository.BookViewRepository;
import aivle.infrastructure.adapter.out.projection.model.BookView;
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
public class BookRegisteredEventListener {

    private final BookViewRepository bookViewRepository;
    private final EventToProjectionMapper mapper;

    @Transactional
    @StreamListener(
            value = KafkaProcessor.INPUT,
            condition = "headers['type']=='BookRegistered'"
    )
    public void onBookRegistered(@Payload BookRegistered evt) {
        log.debug("Received BookRegistered event: {}", evt);
        try {
            if (!evt.validate()) return;
            BookView bookView = mapper.toBookView(evt);
            bookViewRepository.save(bookView);
        } catch (Exception e) {
            throw new CustomException(INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
