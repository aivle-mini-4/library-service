package aivle.infrastructure.adapter.out;

import aivle.application.port.out.BookViewCommandPort;
import aivle.infrastructure.persistence.BookViewRepository;
import aivle.infrastructure.projection.BookView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookViewCommandAdapter implements BookViewCommandPort {

    private final BookViewRepository repo;

    @Override
    public BookView save(BookView bookView) {
        return repo.save(bookView);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
