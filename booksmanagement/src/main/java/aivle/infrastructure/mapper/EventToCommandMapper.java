package aivle.infrastructure.mapper;

import aivle.application.dto.BookRegisterCommand;
import aivle.infrastructure.adapter.out.event.ReadyToPublish;
import org.springframework.stereotype.Component;

@Component
public class EventToCommandMapper {
    public BookRegisterCommand toRegisterCommand(ReadyToPublish evt) {
        return BookRegisterCommand.builder()
                .authorId(evt.getAuthorId())
                .title(evt.getTitle())
                .content(evt.getContent())
                .category(evt.getCategory())
                .summary(evt.getSummary())
                .coverImageUrl(evt.getCoverImageUrl())
                .price(evt.getPrice())
                .build();
    }
}
