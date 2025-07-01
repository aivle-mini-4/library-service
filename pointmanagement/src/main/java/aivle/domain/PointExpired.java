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
public class PointExpired extends AbstractEvent {

    private Long id;
    private Long userId;
    private Integer points;
    private LocalDateTime history;

    public PointExpired(Point aggregate) {
        super(aggregate);
        this.id = aggregate.getId();
        this.userId = aggregate.getUserId();
        this.points = aggregate.getPoints();
        this.history = aggregate.getHistory();
    }

    public PointExpired() {
        super();
    }
}
//>>> DDD / Domain Event
