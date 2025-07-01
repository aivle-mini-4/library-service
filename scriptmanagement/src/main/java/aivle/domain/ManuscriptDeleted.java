package aivle.domain;

import aivle.infra.AbstractEvent;
import lombok.*;

@Data
public class ManuscriptDeleted extends AbstractEvent {

    private Long id;

    public ManuscriptDeleted(Manuscript aggregate) {
        super(aggregate);
    }

    public ManuscriptDeleted() {
        super();
    }
}
