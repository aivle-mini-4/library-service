package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class MemberProfileUpdated extends AbstractEvent {

    private Long id;
    private String name;
    private String email;
    private String roles;
    private String basicInformation;
    private Date updatedAt;

    public MemberProfileUpdated(MemberProfile aggregate) {
        super(aggregate);
    }

    public MemberProfileUpdated() {
        super();
    }
}
//>>> DDD / Domain Event
