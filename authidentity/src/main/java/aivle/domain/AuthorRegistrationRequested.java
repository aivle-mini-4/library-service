package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class AuthorRegistrationRequested extends AbstractEvent {

    private Long id;
    private String email;
    private String selfIntroduction;
    private String portfolio;

    public AuthorRegistrationRequested(AuthorAccount aggregate) {
        super(aggregate);
    }

    public AuthorRegistrationRequested() {
        super();
    }
}
//>>> DDD / Domain Event
