package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class PointPolicyCreated extends AbstractEvent {

    private String id;
    private String name;
    private String description;
    private PointType pointType;
    private Integer amount;
    private Boolean isActive;
    private LocalDateTime createdAt;

    public PointPolicyCreated(Pointpolicy aggregate) {
        super(aggregate);
    }

    public PointPolicyCreated() {
        super();
    }
}
//>>> DDD / Domain Event
