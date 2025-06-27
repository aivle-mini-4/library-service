package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class PointUsed extends AbstractEvent {

    private Long id;
    private Integer points;
    private Date history;
    private Long userId;

    public PointUsed(Point aggregate) {
        super(aggregate);
    }

    public PointUsed() {
        super();
    }
}
//>>> DDD / Domain Event
