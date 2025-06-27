package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class Subscribed extends AbstractEvent {

    private Long id;
    private String name;
    private Boolean isSubscribed;
    private Date updatedAt;

    public Subscribed(Subscribe aggregate) {
        super(aggregate);
    }

    public Subscribed() {
        super();
    }
}
//>>> DDD / Domain Event
