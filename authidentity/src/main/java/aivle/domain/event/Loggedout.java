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
public class Loggedout extends AbstractEvent {

    private Long id;

    public Loggedout(UserAccount aggregate) {
        super(aggregate);
    }

    public Loggedout(AdminAccount aggregate) {
        super(aggregate);
    }

    public Loggedout(AuthorAccount aggregate) {
        super(aggregate);
    }

    public Loggedout() {
        super();
    }
}
//>>> DDD / Domain Event
