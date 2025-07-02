package aivle.domain.event;

import aivle.infrastructure.messaging.AbstractEvent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookUpdated extends AbstractEvent {
    private Long bookId;
    private Long authorId;
    private String title;
    private String content;
    private String category;
    private String summary;
    private String coverImageUrl;
    private Integer price;
    private Long views;
}
