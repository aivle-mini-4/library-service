package aivle.application.mapper;

import aivle.application.dto.BookRegisterCommand;
import aivle.application.dto.BookViewDto;
import aivle.domain.event.BookDeleted;
import aivle.domain.event.BookRegistered;
import aivle.domain.event.BookUpdated;
import aivle.domain.model.Book;
import aivle.infrastructure.adapter.out.projection.model.BookView;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public Book toBookDomain(BookRegisterCommand cmd) {
        return Book.builder()
                .authorId(cmd.getAuthorId())
                .title(cmd.getTitle())
                .content(cmd.getContent())
                .category(cmd.getCategory())
                .summary(cmd.getSummary())
                .coverImageUrl(cmd.getCoverImageUrl())
                .price(cmd.getPrice())
                .build();
    }

    public BookViewDto toBookViewDto(BookView view) {
        return BookViewDto.builder()
                .bookId(view.getBookId())
                .authorId(view.getAuthorId())
                .title(view.getTitle())
                .content(view.getContent())
                .category(view.getCategory())
                .summary(view.getSummary())
                .coverImageUrl(view.getCoverImageUrl())
                .price(view.getPrice())
                .views(view.getViews())
                .build();
    }

    public BookRegistered toRegisterEvent(Book saved) {
        return BookRegistered.builder()
                .bookId(saved.getId())
                .authorId(saved.getAuthorId())
                .title(saved.getTitle())
                .content(saved.getContent())
                .category(saved.getCategory())
                .summary(saved.getSummary())
                .coverImageUrl(saved.getCoverImageUrl())
                .price(saved.getPrice())
                .views(saved.getViews())
                .build();
    }

    public BookDeleted toDeleteEvent(Long bookId) {
        return BookDeleted.builder()
                .bookId(bookId)
                .build();
    }

    public BookUpdated toUpdateEvent(Book saved) {
        return BookUpdated.builder()
                .bookId(saved.getId())
                .authorId(saved.getAuthorId())
                .title(saved.getTitle())
                .content(saved.getContent())
                .category(saved.getCategory())
                .summary(saved.getSummary())
                .coverImageUrl(saved.getCoverImageUrl())
                .price(saved.getPrice())
                .views(saved.getViews())
                .build();
    }
}