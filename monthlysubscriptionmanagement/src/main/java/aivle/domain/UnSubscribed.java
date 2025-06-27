package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class UnSubscribed extends AbstractEvent {

    private Long id;

    public UnSubscribed(Subscribe aggregate) {
        super(aggregate);
    }

    public UnSubscribed() {
        super();
    }
}
//>>> DDD / Domain Event
