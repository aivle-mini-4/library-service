package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.util.Date;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class AuthorApproved extends AbstractEvent {

    private Long id;
    private Long authorId;
    private Date resultAt;
    private ApprovalState state;

    public AuthorApproved(Authorapproval aggregate) {
        super(aggregate);
        this.id = aggregate.getId();
        this.authorId = aggregate.getAuthorId();
        this.resultAt = aggregate.getResultAt();
        this.state = aggregate.getState();
    }

    public AuthorApproved() {
        super();
    }
}
//>>> DDD / Domain Event
