package aivle.domain.event;

import java.time.LocalDateTime;

import aivle.domain.entity.AdminAccount;
import aivle.domain.entity.UserAccount;
import aivle.infra.event.AbstractEvent;
import lombok.Data;
import lombok.ToString;

//<<< DDD / Domain Event
@Data
@ToString
public class SignedUp extends AbstractEvent {

    private Long id;
    private String email;
    private String password;
    private String roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public SignedUp(UserAccount aggregate) {
        super(aggregate);
    }

    public SignedUp(AdminAccount aggregate) {
        super(aggregate);
    }

    public SignedUp() {
        super();
    }
}
//>>> DDD / Domain Event
