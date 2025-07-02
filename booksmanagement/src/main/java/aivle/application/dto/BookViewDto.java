package aivle.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookViewDto {
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
