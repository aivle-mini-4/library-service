package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class FavoriteRegistrerd extends AbstractEvent {

    private Long id;
    private Integer bookId;
    private Integer userId;

    public FavoriteRegistrerd(Favorite aggregate) {
        super(aggregate);
    }

    public FavoriteRegistrerd() {
        super();
    }
}
//>>> DDD / Domain Event
