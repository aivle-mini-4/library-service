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

    public PointPolicyDeleted(Pointpolicy aggregate) {
        super(aggregate);
    }

    public PointPolicyDeleted() {
        super();
    }
}
//>>> DDD / Domain Event
