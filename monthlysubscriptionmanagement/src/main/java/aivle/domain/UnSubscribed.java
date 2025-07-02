package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class UnSubscribed extends AbstractEvent {

    private Long id;
    private LocalDateTime updatedAt;

    public UnSubscribed(Subscribe aggregate) {
        super(aggregate);
        this.id = aggregate.getId();
        this.updatedAt = aggregate.getUpdatedAt();
    }

    public UnSubscribed() {
        super();
    }
}
//>>> DDD / Domain Event
