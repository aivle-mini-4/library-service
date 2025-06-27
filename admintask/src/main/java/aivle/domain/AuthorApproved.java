package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class AuthorApproved extends AbstractEvent {

    private Long id;
    private Long authorId;
    private Date resultAt;
    private String state;

    public AuthorApproved(Authorapproval aggregate) {
        super(aggregate);
    }

    public AuthorApproved() {
        super();
    }
}
//>>> DDD / Domain Event
