package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class MemberProfileCreated extends AbstractEvent {

    private Long id;
    private String name;
    private String email;
    private String roles;
    private String basicInformation;
    private Date updatedAt;

    public MemberProfileCreated(MemberProfile aggregate) {
        super(aggregate);
    }

    public MemberProfileCreated() {
        super();
    }
}
//>>> DDD / Domain Event
