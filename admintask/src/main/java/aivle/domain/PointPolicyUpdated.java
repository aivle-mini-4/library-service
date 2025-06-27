package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class PointPolicyUpdated extends AbstractEvent {

    private Long id;
    private String name;
    private String description;
    private PointType pointType;
    private Integer amount;
    private Boolean isActive;
    private LocalDateTime updatedAt;

    public PointPolicyUpdated(Pointpolicy aggregate) {
        super(aggregate);
    }

    public PointPolicyUpdated() {
        super();
    }
}
//>>> DDD / Domain Event
