//PointPolicyCreated.java
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

    private Long id;
    private String name;
    private String description;
    private PointType pointType;
    private Integer amount;
    private Boolean isActive;
    private LocalDateTime createdAt;

    public PointPolicyCreated(Pointpolicy aggregate) {
        super(aggregate);
        this.id = aggregate.getId();
        this.name = aggregate.getName();
        this.description = aggregate.getDescription();
        this.pointType = aggregate.getPointType();
        this.amount = aggregate.getAmount();
        this.isActive = aggregate.getIsActive();
        this.createdAt = aggregate.getCreatedAt();
    }

    public PointPolicyCreated() {
        super();
    }
}
//>>> DDD / Domain Event
