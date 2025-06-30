package aivle.infrastructure.adapter.out;

import aivle.application.port.out.BookViewQueryPort;
import aivle.common.exception.CustomException;
import aivle.infrastructure.persistence.BookViewRepository;
import aivle.infrastructure.projection.BookView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static aivle.common.response.ErrorCode.NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class BookViewQueryAdapter implements BookViewQueryPort {

    private final BookViewRepository repo;

    @Override
    public List<BookView> findAll() {
        return (List<BookView>) repo.findAll();
    }

    @Override
    public BookView findById(Long bookId) {
        return repo.findById(bookId).orElseThrow(() -> new CustomException(NOT_FOUND, "도서를 찾을 수 없습니다."));
    }
}
