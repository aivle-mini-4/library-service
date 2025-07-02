package aivle.application.service;

import aivle.application.dto.BookDeleteCommand;
import aivle.application.dto.BookRegisterCommand;
import aivle.application.mapper.BookMapper;
import aivle.application.port.in.BookCommandUseCase;
import aivle.application.port.out.BookCommandPort;
import aivle.domain.event.BookDeleted;
import aivle.domain.event.BookRegistered;
import aivle.domain.event.BookUpdated;
import aivle.domain.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookCommandService implements BookCommandUseCase {

    private final BookCommandPort bookCommandPort;
    private final BookMapper bookMapper;

    @Override
    public void registerBook(BookRegisterCommand command) {
        Book book = bookMapper.toBookDomain(command);
        Book savedBook = bookCommandPort.save(book);

        BookRegistered event = bookMapper.toRegisterEvent(savedBook);
        event.publishAfterCommit();
    }

    @Override
    public void deleteBook(BookDeleteCommand cmd) {
        bookCommandPort.deleteById(cmd.getBookId());

        BookDeleted event = bookMapper.toDeleteEvent(cmd.getBookId());
        event.publishAfterCommit();
    }

    @Override
    public void increaseViewCount(Long bookId) {
        Book book = bookCommandPort.findById(bookId);
        book.increaseViews();
        Book savedBook = bookCommandPort.save(book);

        BookUpdated event = bookMapper.toUpdateEvent(savedBook);
        event.publishAfterCommit();
    }
}
