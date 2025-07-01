package aivle.domain.event;

import aivle.infrastructure.messaging.AbstractEvent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookDeleted extends AbstractEvent {
    private Long bookId;
}
