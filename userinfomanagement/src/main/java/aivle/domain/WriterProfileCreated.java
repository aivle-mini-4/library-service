package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class WriterProfileCreated extends AbstractEvent {

    private Long id;
    private String name;
    private String email;
    private String roles;
    private String basicInformation;
    private String selfIntroduction;
    private String portfolio;
    private Date updatedAt;

    public WriterProfileCreated(WriterProfile aggregate) {
        super(aggregate);
    }

    public WriterProfileCreated() {
        super();
    }
}
//>>> DDD / Domain Event
