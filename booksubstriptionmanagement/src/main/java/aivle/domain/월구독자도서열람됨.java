package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class 월구독자도서열람됨 extends AbstractEvent {

    private Long id;

    public 월구독자도서열람됨(BookSubscription aggregate) {
        super(aggregate);
    }

    public 월구독자도서열람됨() {
        super();
    }
}
//>>> DDD / Domain Event
