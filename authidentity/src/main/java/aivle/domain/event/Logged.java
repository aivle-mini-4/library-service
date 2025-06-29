package aivle.domain.event;

import aivle.domain.entity.AdminAccount;
import aivle.domain.entity.AuthorAccount;
import aivle.domain.entity.UserAccount;
import aivle.infra.AbstractEvent;
import lombok.Data;
import lombok.ToString;

//<<< DDD / Domain Event
@Data
@ToString
public class Logged extends AbstractEvent {

    private Long id;
    private String email;
    private String password;
    private String roles;

    public Logged(UserAccount aggregate) {
        super(aggregate);
    }

    public Logged(AdminAccount aggregate) {
        super(aggregate);
    }

    public Logged(AuthorAccount aggregate) {
        super(aggregate);
    }

    public Logged() {
        super();
    }
}
//>>> DDD / Domain Event
