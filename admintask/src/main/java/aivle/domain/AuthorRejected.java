package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.util.Date;
import java.util.*;
import lombok.*;


//<<< DDD / Domain Event
@Data
@ToString
public class AuthorRejected extends AbstractEvent {

    private Long id;
    private String resultAt;
    private Long authorId;
    private ApprovalState state;

    public AuthorRejected(Authorapproval aggregate) {
        super(aggregate);
        this.id = aggregate.getId();
        this.authorId = aggregate.getAuthorId();
        this.resultAt = aggregate.getResultAt();
        this.state = aggregate.getState();
    }

    public AuthorRejected() {
        super();
    }
}
//>>> DDD / Domain Event
