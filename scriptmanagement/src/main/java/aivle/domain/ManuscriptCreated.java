package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class ManuscriptCreated extends AbstractEvent {

    private Long id;
    private Long authorId;
    private String title;
    private String content;
    private String updatedAt;

    public ManuscriptCreated(Manuscript aggregate) {
        super(aggregate);
    }

    public ManuscriptCreated() {
        super();
    }
}
//>>> DDD / Domain Event
