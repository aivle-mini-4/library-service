package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class Loggedout extends AbstractEvent {

    private Long id;

    public Loggedout(UserAccount aggregate) {
        super(aggregate);
    }

    public Loggedout() {
        super();
    }
}
//>>> DDD / Domain Event
