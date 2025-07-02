package aivle.application.mapper;

import aivle.application.dto.BestSellerViewDto;
import aivle.infrastructure.adapter.out.projection.model.BestSellerView;
import org.springframework.stereotype.Component;

@Component
public class BestSellerMapper {
    public BestSellerViewDto toBestSellerViewDto(BestSellerView view) {
        return BestSellerViewDto.builder()
                .bookId(view.getBookId())
                .authorId(view.getAuthorId())
                .title(view.getTitle())
                .category(view.getCategory())
                .coverImageUrl(view.getCoverImageUrl())
                .price(view.getPrice())
                .views(view.getViews())
                .build();
    }
}
