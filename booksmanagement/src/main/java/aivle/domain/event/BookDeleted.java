package aivle.domain.event;

import aivle.domain.model.Book;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class BookDeleted extends AbstractEvent {

    private Long id;

    public BookDeleted(Book aggregate) {
        super(aggregate);
    }

    public BookDeleted() {
        super();
    }
}
//>>> DDD / Domain Event
