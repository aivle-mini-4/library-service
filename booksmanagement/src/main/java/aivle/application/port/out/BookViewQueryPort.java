package aivle.application.port.out;

import aivle.infrastructure.projection.BookView;

import java.util.List;

public interface BookViewQueryPort {
    List<BookView> findAll();

    BookView findById(Long bookId);
}
