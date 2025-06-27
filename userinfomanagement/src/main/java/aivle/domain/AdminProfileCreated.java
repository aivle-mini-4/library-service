package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class AdminProfileCreated extends AbstractEvent {

    private Long id;
    private String name;
    private String email;
    private String roles;

    public AdminProfileCreated(AdminProfile aggregate) {
        super(aggregate);
    }

    public AdminProfileCreated() {
        super();
    }
}
//>>> DDD / Domain Event
