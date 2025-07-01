package aivle.domain;

import aivle.infra.AbstractEvent;
import java.time.LocalDateTime;
import lombok.*;

@Data
public class ManuscriptUpdated extends AbstractEvent {

    private Long id;
    private Long authorId;
    private String title;
    private String content;
    private LocalDateTime updatedAt;

    public ManuscriptUpdated(Manuscript aggregate) {
        super(aggregate);
    }

    public ManuscriptUpdated() {
        super();
    }
}
