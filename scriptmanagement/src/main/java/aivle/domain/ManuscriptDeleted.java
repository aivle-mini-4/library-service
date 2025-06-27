package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class ManuscriptDeleted extends AbstractEvent {

    private Long id;

    public ManuscriptDeleted(Manuscript aggregate) {
        super(aggregate);
    }

    public ManuscriptDeleted() {
        super();
    }
}
//>>> DDD / Domain Event
