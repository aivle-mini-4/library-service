package aivle.domain.Author;

import aivle.infra.AbstractEvent;
import java.time.LocalDateTime;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class AuthorProfileCreated extends AbstractEvent {
    private Long id;
    private String name;
    private String email;
    private String roles;
    private String basicInformation;
    private String selfIntroduction;
    private String portfolio;
    private LocalDateTime updatedAt;

    public AuthorProfileCreated(AuthorProfile aggregate) {
        super(aggregate);
        this.id = aggregate.getId();
        this.name = aggregate.getName();
        this.email = aggregate.getEmail();
        this.roles = aggregate.getRoles();
        this.basicInformation = aggregate.getBasicInformation();
        this.selfIntroduction = aggregate.getSelfIntroduction();
        this.portfolio = aggregate.getPortfolio();
        this.updatedAt = aggregate.getUpdatedAt();
    }

    public AuthorProfileCreated() {
        super();
    }
}
//>>> DDD / Domain Event
