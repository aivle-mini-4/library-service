package aivle.infrastructure.mapper;

import aivle.domain.event.BookRegistered;
import aivle.infrastructure.projection.BookView;
import org.springframework.stereotype.Component;

@Component
public class DomainToProjectionMapper {
    public BookView toBookView(BookRegistered bookRegistered) {
        return BookView.builder()
                .bookId(bookRegistered.getId())
                .authorId(bookRegistered.getAuthorId())
                .title(bookRegistered.getTitle())
                .content(bookRegistered.getContent())
                .category(bookRegistered.getCategory())
                .summary(bookRegistered.getSummary())
                .coverImageUrl(bookRegistered.getCoverImageUrl())
                .price(bookRegistered.getPrice())
                .views(bookRegistered.getViews())
                .build();
    }
}
