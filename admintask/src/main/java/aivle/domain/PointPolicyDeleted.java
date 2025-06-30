package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class PointPolicyDeleted extends AbstractEvent {

    private Long id;
    private final String name;

    public PointPolicyDeleted(Pointpolicy aggregate) {
        super(aggregate);
        this.id = aggregate.getId();
        this.name = aggregate.getName();
    }

    public PointPolicyDeleted() {
        super();
    }
}
//>>> DDD / Domain Event
