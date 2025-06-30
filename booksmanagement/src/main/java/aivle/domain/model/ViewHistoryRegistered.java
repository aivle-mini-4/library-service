package aivle.domain.model;

import aivle.domain.event.AbstractEvent;
import lombok.*;

@Data
@ToString
public class ViewHistoryRegistered extends AbstractEvent {

    private Long id;
    private Integer bookId;
    private Integer userId;
}
