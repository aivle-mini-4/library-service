package aivle.infrastructure.adapter.in.event;

import aivle.application.dto.BookRegisterCommand;
import aivle.application.port.in.BookCommandUseCase;
import aivle.common.exception.CustomException;
import aivle.infrastructure.mapper.EventToCommandMapper;
import aivle.infrastructure.messaging.KafkaProcessor;
import aivle.infrastructure.adapter.out.event.ReadyToPublish;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static aivle.common.response.ErrorCode.INTERNAL_SERVER_ERROR;

@Component

@RequiredArgsConstructor
@Slf4j
public class ReadyToPublishEventListener {

    private final BookCommandUseCase bookCommandUseCase;
    private final EventToCommandMapper mapper;

    @StreamListener(
            value = KafkaProcessor.INPUT,
            condition = "headers['type']=='ReadyToPublish'"
    )
    public void handleReadyToPublish(@Payload ReadyToPublish evt) {
        log.debug("Received ReadyToPublish event: {}", evt);
        try {
            if (!evt.validate()) return;
            BookRegisterCommand command = mapper.toRegisterCommand(evt);
            bookCommandUseCase.registerBook(command);
        } catch (Exception e) {
            throw new CustomException(INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
