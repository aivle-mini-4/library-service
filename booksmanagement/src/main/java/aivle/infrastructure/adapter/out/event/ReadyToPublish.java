package aivle.infrastructure.adapter.out.event;

import aivle.infrastructure.messaging.AbstractEvent;
import lombok.Getter;

@Getter
public class ReadyToPublish extends AbstractEvent {
    private Long authorId;
    private String title;
    private String content;
    private String category;
    private String summary;
    private String coverImageUrl;
    private Integer price;
}
