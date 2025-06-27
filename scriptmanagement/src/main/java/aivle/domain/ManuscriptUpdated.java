package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class ManuscriptUpdated extends AbstractEvent {

    private Long id;
    private Long authorId;
    private String title;
    private String content;
    private String updatedAt;

    public ManuscriptUpdated(Manuscript aggregate) {
        super(aggregate);
    }

    public ManuscriptUpdated() {
        super();
    }
}
//>>> DDD / Domain Event
