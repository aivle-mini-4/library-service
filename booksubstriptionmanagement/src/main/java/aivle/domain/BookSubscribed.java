package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class BookSubscribed extends AbstractEvent {

    private Long id;
    private Long bookId;
    private String name;
    private Boolean isBookSubscribed;
    private Date updatedAt;

    public BookSubscribed(BookSubscription aggregate) {
        super(aggregate);
    }

    public BookSubscribed() {
        super();
    }
}
//>>> DDD / Domain Event
