package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class ViewHistoryRegistered extends AbstractEvent {

    private Long id;
    private Integer bookId;
    private Integer userId;

    public ViewHistoryRegistered(ViewHistory aggregate) {
        super(aggregate);
    }

    public ViewHistoryRegistered() {
        super();
    }
}
//>>> DDD / Domain Event
