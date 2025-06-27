package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class BookDeleted extends AbstractEvent {

    private Long id;

    public BookDeleted(Books aggregate) {
        super(aggregate);
    }

    public BookDeleted() {
        super();
    }
}
//>>> DDD / Domain Event
