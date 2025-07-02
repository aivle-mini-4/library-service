package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.time.LocalDateTime;
import java.util.*;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//<<< DDD / Domain Event
@Data
@ToString
public class AuthorApproved extends AbstractEvent {

    private Long id;
    private Long adminId;
    private Long authorId;
    private LocalDateTime resultAt;
    private ApprovalState state;

    public AuthorApproved(Authorapproval aggregate) {
        super(aggregate);
    }

    public AuthorApproved() {
        super();
    }
}
//>>> DDD / Domain Event
