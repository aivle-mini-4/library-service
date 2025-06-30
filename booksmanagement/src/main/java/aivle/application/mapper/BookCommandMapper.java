package aivle.application.mapper;

import aivle.application.dto.BookRegisterCommand;
import aivle.domain.event.BookRegistered;
import aivle.domain.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookCommandMapper {
    public Book toDomain(BookRegisterCommand cmd) {
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

    public BookRegistered toDomainEvent(Book saved) {
        return BookRegistered.builder()
                .id(saved.getId())
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
