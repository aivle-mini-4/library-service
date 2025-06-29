package aivle.domain;

import aivle.infra.AbstractEvent;
import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;

import lombok.*;

@Getter
@Setter
@ToString
public class ManuscriptCreated extends AbstractEvent {

    private Long id;
    private Long authorId;
    private String title;
    private String content;
    private LocalDateTime updatedAt;

    public ManuscriptCreated(Manuscript aggregate) {
        BeanUtils.copyProperties(aggregate, this);
    }

    public ManuscriptCreated() {
        super();
    }
}
