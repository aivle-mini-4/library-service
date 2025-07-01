package aivle.domain.event;

import aivle.domain.entity.AuthorAccount;
import aivle.infra.event.AbstractEvent;
import lombok.Data;
import lombok.ToString;

//<<< DDD / Domain Event
@Data
@ToString
public class AuthorSignup extends AbstractEvent {

    private Long id;
    private String email;
    private String selfIntroduction;
    private String portfolio;

    public AuthorSignup(AuthorAccount aggregate) {
        super(aggregate);
    }

    public AuthorSignup() {
        super();
    }
}
//>>> DDD / Domain Event
