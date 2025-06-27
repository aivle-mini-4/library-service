package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class PointExpired extends AbstractEvent {

    private Long id;
    private Long userId;
    private Integer points;
    private Date history;

    public PointExpired(Point aggregate) {
        super(aggregate);
    }

    public PointExpired() {
        super();
    }
}
//>>> DDD / Domain Event
