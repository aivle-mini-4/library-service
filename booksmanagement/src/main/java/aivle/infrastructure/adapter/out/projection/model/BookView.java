package aivle.infrastructure.adapter.out.projection.model;

import aivle.common.entity.BaseEntity;
import aivle.domain.event.BookUpdated;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

import static lombok.AccessLevel.PROTECTED;

@Entity

@Getter
@NoArgsConstructor(access = PROTECTED)
public class BookView extends BaseEntity {

    @Id
    private Long bookId;

    private Long authorId;

    private String title;
    private String content;
    private String category;
    private String summary;
    private String coverImageUrl;

    private Integer price;
    private Long views;

    @Builder
    public BookView(Long bookId, Long authorId, String title, String content, String category, String summary, String coverImageUrl, Integer price, Long views) {
        this.bookId = bookId;
        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.category = category;
        this.summary = summary;
        this.coverImageUrl = coverImageUrl;
        this.price = price;
        this.views = views;
    }

    public void updateFromEvent(BookUpdated evt) {
        this.bookId = evt.getBookId();
        this.authorId = evt.getAuthorId();
        this.title = evt.getTitle();
        this.content = evt.getContent();
        this.category = evt.getCategory();
        this.summary = evt.getSummary();
        this.coverImageUrl = evt.getCoverImageUrl();
        this.price = evt.getPrice();
        this.views = evt.getViews();
    }
}