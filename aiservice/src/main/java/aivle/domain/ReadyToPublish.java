package aivle.domain;

import aivle.infra.AbstractEvent;
import aivle.domain.AiService.ProcessingResult;
import lombok.Data;

@Data
public class ReadyToPublish extends AbstractEvent {

    private Long authorId;
    private String coverImageUrl;
    private String title;
    private String summary;
    private String category;
    private String price;
    private String content;

    public ReadyToPublish(Long authorId, String title, String content, ProcessingResult result) {
        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.coverImageUrl = result.getCoverImageUrl();
        this.summary = result.getSummary();
        this.category = result.getCategories();
        this.price = result.getEstimatedPrice();
    }

    public ReadyToPublish() {
        super();
    }
}
