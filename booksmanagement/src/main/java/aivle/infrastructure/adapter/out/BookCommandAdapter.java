package aivle.infrastructure.adapter.out;

import aivle.application.port.out.BookCommandPort;
import aivle.domain.model.Book;
import aivle.infrastructure.persistence.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookCommandAdapter implements BookCommandPort {

    private final BookRepository repo;

    @Override
    public Book save(Book book) {
        return repo.save(book);
    }

}
