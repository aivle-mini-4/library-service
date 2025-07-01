package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.time.LocalDateTime;
import java.util.*;
import lombok.*;


//<<< DDD / Domain Event
@Data
@ToString
public class AuthorRejected extends AbstractEvent {

    private Long id;
    private String resultAt;
    private Long authorId;
    private Long adminId;
    private ApprovalState state;

    public AuthorRejected(Authorapproval aggregate) {
        super(aggregate);

    }

    public AuthorRejected() {
        super();
    }
}
//>>> DDD / Domain Event
