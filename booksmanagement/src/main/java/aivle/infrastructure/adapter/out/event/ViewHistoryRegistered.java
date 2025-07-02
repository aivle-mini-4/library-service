package aivle.infrastructure.adapter.out.event;

import aivle.infrastructure.messaging.AbstractEvent;
import lombok.Getter;

@Getter
public class ViewHistoryRegistered extends AbstractEvent {
    private Long bookId;
}