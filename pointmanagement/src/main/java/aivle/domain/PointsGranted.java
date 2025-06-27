package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class PointsGranted extends AbstractEvent {

    private Long id;
    private Integer points;
    private Date history;
    private Long userId;

    public PointsGranted(Point aggregate) {
        super(aggregate);
    }

    public PointsGranted() {
        super();
    }
}
//>>> DDD / Domain Event
