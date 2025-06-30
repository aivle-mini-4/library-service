package aivle.application.port.out;

import aivle.infrastructure.projection.BookView;

public interface BookViewCommandPort {
    BookView save(BookView bookView);

    void deleteById(Long id);
}
