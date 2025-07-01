package aivle.domain.model;

import aivle.common.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Book extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long authorId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String category;
    private String summary;
    private String coverImageUrl;

    private Integer price;
    private Long views;

    @Builder
    public Book(Long authorId, String title, String content, String category, String summary, String coverImageUrl, Integer price) {
        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.category = category;
        this.summary = summary;
        this.coverImageUrl = coverImageUrl;
        this.price = price;
        this.views = 0L;
    }

    public long increaseViews() {
        this.views++;
        return this.views;
    }
}
