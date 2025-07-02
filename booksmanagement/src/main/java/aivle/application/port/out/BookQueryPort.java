package aivle.application.port.out;

import aivle.infrastructure.adapter.out.projection.model.BookView;

import java.util.List;

public interface BookQueryPort {
    List<BookView> findAll();
    BookView findById(Long bookId);
}
