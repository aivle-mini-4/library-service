package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class ReadyToPublish extends AbstractEvent {

    private Long id;
    private String authorId;
    private String coverImageUrl;
    private String title;
    private String summary;
    private String category;
    private String price;
    private String content;
    private String views;

    public ReadyToPublish(AiService aggregate) {
        super(aggregate);
    }

    public ReadyToPublish() {
        super();
    }
}
//>>> DDD / Domain Event
