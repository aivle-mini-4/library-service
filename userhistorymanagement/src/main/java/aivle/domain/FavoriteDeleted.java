package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class FavoriteDeleted extends AbstractEvent {

    private Long id;
    private Integer bookId;
    private Integer userId;

    public FavoriteDeleted(Favorite aggregate) {
        super(aggregate);
    }

    public FavoriteDeleted() {
        super();
    }
}
//>>> DDD / Domain Event
