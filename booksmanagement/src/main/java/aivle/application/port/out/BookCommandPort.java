package aivle.application.port.out;

import aivle.domain.model.Book;

public interface BookCommandPort {
    Book save(Book book);

    void deleteById(Long bookId);

    Book findById(Long bookId);
}
