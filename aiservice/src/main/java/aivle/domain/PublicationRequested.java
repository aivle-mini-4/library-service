package aivle.domain;

import aivle.infra.AbstractEvent;
import lombok.Data;

@Data
public class PublicationRequested extends AbstractEvent {

    private Long id;
    private Long authorId;
    private String title;
    private String content;
    private String updatedAt;
}
