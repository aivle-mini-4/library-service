package aivle.application.service;

import aivle.application.dto.BookRegisterCommand;
import aivle.application.mapper.BookCommandMapper;
import aivle.application.port.in.BookCommandUseCase;
import aivle.application.port.out.BookCommandPort;
import aivle.domain.event.BookRegistered;
import aivle.domain.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookCommandService implements BookCommandUseCase {

    private final BookCommandPort bookRepository;
    private final BookCommandMapper mapper;

    @Override
    @Transactional
    public void registerBook(BookRegisterCommand command) {
        Book book = mapper.toDomain(command);
        Book savedBook = bookRepository.save(book);

        BookRegistered event = mapper.toDomainEvent(savedBook);
        event.publishAfterCommit();
    }
}
