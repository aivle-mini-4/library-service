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
public class PointsGranted extends AbstractEvent {

    private Long id;
    private Integer points;
    private LocalDateTime history;
    private Long userId;

    public PointsGranted(Point aggregate) {
        super(aggregate);
        this.id = aggregate.getId();
        this.userId = aggregate.getUserId();
        this.points = aggregate.getPoints();
        this.history = aggregate.getHistory();
    }

    public PointsGranted() {
        super();
    }
}
//>>> DDD / Domain Event
