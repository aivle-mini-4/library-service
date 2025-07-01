package aivle.infrastructure.adapter.out.persistence;

import aivle.application.port.out.BookCommandPort;
import aivle.common.exception.CustomException;
import aivle.domain.model.Book;
import aivle.infrastructure.adapter.out.persistence.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static aivle.common.response.ErrorCode.NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class BookCommandAdapter implements BookCommandPort {

    private final BookRepository repo;

    @Override
    public Book findById(Long bookId) {
        return repo.findById(bookId).orElseThrow(() -> new CustomException(NOT_FOUND, "도서를 찾을 수 없습니다."));
    }

    @Override
    public Book save(Book book) {
        return repo.save(book);
    }

    @Override
    public void deleteById(Long bookId) {
        repo.deleteById(bookId);
    }
}
