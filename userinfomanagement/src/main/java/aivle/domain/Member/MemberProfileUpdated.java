package aivle.domain.Member;

import aivle.infra.AbstractEvent;
import java.time.LocalDateTime;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class MemberProfileUpdated extends AbstractEvent {
    private Long id;
    private String name;
    private String email;
    private String basicInformation;
    private LocalDateTime updatedAt;

    public MemberProfileUpdated(MemberProfile aggregate) {
        super(aggregate);
        this.id = aggregate.getId();
        this.name = aggregate.getName();
        this.email = aggregate.getEmail();
        this.basicInformation = aggregate.getBasicInformation();
        this.updatedAt = aggregate.getUpdatedAt();
    }

    public MemberProfileUpdated() {
        super();
    }
}  
//>>> DDD / Domain Event
