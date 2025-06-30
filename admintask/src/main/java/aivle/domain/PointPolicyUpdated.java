package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.util.Date;
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
    private Date updatedAt;

    public PointPolicyUpdated(Pointpolicy aggregate) {
        super(aggregate);
        this.id = aggregate.getId();
        this.name = aggregate.getName();
        this.description = aggregate.getDescription();
        this.pointType = aggregate.getPointType();
        this.amount = aggregate.getAmount();
        this.isActive = aggregate.getIsActive();
        this.updatedAt = aggregate.getUpdatedAt();
    }

    public PointPolicyUpdated() {
        super();
    }
}
//>>> DDD / Domain Event
