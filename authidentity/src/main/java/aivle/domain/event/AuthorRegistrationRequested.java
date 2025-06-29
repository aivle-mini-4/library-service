package aivle.domain.event;

import aivle.domain.entity.AuthorAccount;
import aivle.infra.AbstractEvent;
import lombok.Data;
import lombok.ToString;

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
