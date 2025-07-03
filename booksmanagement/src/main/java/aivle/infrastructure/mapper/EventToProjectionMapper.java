package aivle.infrastructure.mapper;

import aivle.domain.event.BookRegistered;
import aivle.domain.event.BookUpdated;
import aivle.infrastructure.adapter.out.projection.model.BestSellerView;
import aivle.infrastructure.adapter.out.projection.model.BookView;
import org.springframework.stereotype.Component;

@Component
public class EventToProjectionMapper {
    public BookView toBookView(BookRegistered bookRegistered) {
        return BookView.builder()
                .bookId(bookRegistered.getBookId())
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

    public BestSellerView toBestSellerView(BookUpdated bookUpdated) {
        return BestSellerView.builder()
                .bookId(bookUpdated.getBookId())
                .authorId(bookUpdated.getAuthorId())
                .title(bookUpdated.getTitle())
                .category(bookUpdated.getCategory())
                .summary(bookUpdated.getSummary())
                .coverImageUrl(bookUpdated.getCoverImageUrl())
                .price(bookUpdated.getPrice())
                .views(bookUpdated.getViews())
                .build();
    }
}
