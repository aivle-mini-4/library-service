package aivle.domain.Author;

import aivle.infra.AbstractEvent;
import java.time.LocalDateTime;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class AuthorProfileUpdated extends AbstractEvent {
    private Long id;
    private String name;
    private String email;
    private String basicInformation;
    private String selfIntroduction;
    private String portfolio;
    private LocalDateTime updatedAt;

    public AuthorProfileUpdated(AuthorProfile aggregate) {
        super(aggregate);
        this.id = aggregate.getId();
        this.name = aggregate.getName();
        this.email = aggregate.getEmail();
        this.basicInformation = aggregate.getBasicInformation();
        this.selfIntroduction = aggregate.getSelfIntroduction();
        this.portfolio = aggregate.getPortfolio();
        this.updatedAt = aggregate.getUpdatedAt();
    }

    public AuthorProfileUpdated() {
        super();
    }
}
//>>> DDD / Domain Event
