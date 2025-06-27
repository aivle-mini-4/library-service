package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class WriterProfileUpdated extends AbstractEvent {

    private Long id;
    private String name;
    private String email;
    private String roles;
    private String basicInformation;
    private String selfIntroduction;
    private String portfolio;
    private Date updatedAt;

    public WriterProfileUpdated(WriterProfile aggregate) {
        super(aggregate);
    }

    public WriterProfileUpdated() {
        super();
    }
}
//>>> DDD / Domain Event
