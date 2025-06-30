package aivle.application.mapper;

import aivle.application.dto.BookViewDto;
import aivle.infrastructure.projection.BookView;
import org.springframework.stereotype.Component;

@Component
public class DomainToDtoMapper {
    public BookViewDto toBookViewDto(BookView bookView) {
        return BookViewDto.builder()
                .bookId(bookView.getBookId())
                .authorId(bookView.getAuthorId())
                .title(bookView.getTitle())
                .content(bookView.getContent())
                .category(bookView.getCategory())
                .summary(bookView.getSummary())
                .coverImageUrl(bookView.getCoverImageUrl())
                .price(bookView.getPrice())
                .views(bookView.getViews())
                .build();
    }
}
