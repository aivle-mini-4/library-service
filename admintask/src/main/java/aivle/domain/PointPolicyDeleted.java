package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.time.LocalDateTime;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class PointPolicyDeleted extends AbstractEvent {

    private Long id;
    private String name;

    public PointPolicyDeleted(Pointpolicy aggregate) {
        super(aggregate);
    }

    public PointPolicyDeleted() {
        super();
    }
}
//>>> DDD / Domain Event
