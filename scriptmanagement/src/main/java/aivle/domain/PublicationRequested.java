package aivle.domain;

import aivle.infra.AbstractEvent;
import java.time.LocalDateTime;
import lombok.*;
import org.springframework.beans.BeanUtils;

@Data
@ToString
public class PublicationRequested extends AbstractEvent {

    private Long id;
    private Long authorId;
    private String title;
    private String content;
    private LocalDateTime updatedAt;

    public PublicationRequested(Manuscript aggregate) {
        super(aggregate);
        BeanUtils.copyProperties(aggregate, this);
    }

    public PublicationRequested() {
        super();
    }
}
