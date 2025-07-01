package aivle.infrastructure.adapter.in.event;

import aivle.application.port.in.BookCommandUseCase;
import aivle.common.exception.CustomException;
import aivle.infrastructure.messaging.KafkaProcessor;
import aivle.infrastructure.adapter.out.event.ViewHistoryRegistered;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static aivle.common.response.ErrorCode.INTERNAL_SERVER_ERROR;

@Component

@RequiredArgsConstructor
@Slf4j
public class ViewHistoryRegisteredListener {

    private final BookCommandUseCase bookCommandUseCase;

    @StreamListener(
            value = KafkaProcessor.INPUT,
            condition = "headers['type']=='ViewHistoryRegistered'"
    )
    public void onViewHistoryRegistered(@Payload ViewHistoryRegistered evt) {
        log.debug("Received ViewHistoryRegistered event: {}", evt);
        try {
            if (!evt.validate()) return;
            bookCommandUseCase.increaseViewCount(evt.getBookId());
        } catch (Exception e) {
            throw new CustomException(INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
