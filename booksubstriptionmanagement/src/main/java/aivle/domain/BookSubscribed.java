package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.time.LocalDateTime;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class BookSubscribed extends AbstractEvent {

    private Long id;
    private Long bookId;
    private Long userId;
    private Integer price;
    private String bookName;
    private Boolean isBookSubscribed;
    private LocalDateTime updatedAt;

    public BookSubscribed(BookSubscription aggregate) {
        super(aggregate);
    }

    public BookSubscribed() {
        super();
    }
}
//>>> DDD / Domain Event
