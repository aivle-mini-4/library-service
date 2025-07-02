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
public class Subscribed extends AbstractEvent {

    private Long id;
    private Long userId;
    private String name;
    private Boolean isSubscribed;
    private LocalDateTime updatedAt;

    public Subscribed(Subscribe aggregate) {
        super(aggregate);
        this.id = aggregate.getId();
        this.userId = aggregate.getUserId();
        this.name = aggregate.getName();
        this.isSubscribed = aggregate.getIsSubscribed();
        this.updatedAt = aggregate.getUpdatedAt();
    }

    public Subscribed() {
        super();
    }
}
//>>> DDD / Domain Event
