package aivle.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BestSellerViewDto {
    private Long bookId;
    private Long authorId;
    private String title;
    private String category;
    private String coverImageUrl;
    private Integer price;
    private Long views;
}
