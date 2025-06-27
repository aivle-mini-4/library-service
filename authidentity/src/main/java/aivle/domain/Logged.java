package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class Logged extends AbstractEvent {

    private Long id;
    private String email;
    private String password;
    private String roles;

    public Logged(UserAccount aggregate) {
        super(aggregate);
    }

    public Logged() {
        super();
    }
}
//>>> DDD / Domain Event
