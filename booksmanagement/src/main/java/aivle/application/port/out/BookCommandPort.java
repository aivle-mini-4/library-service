package aivle.application.port.out;

import aivle.domain.model.Book;

public interface BookCommandPort {
    Book save(Book book);
}
