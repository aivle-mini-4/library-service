package aivle.infrastructure.adapter.out.projection.adapter;

import aivle.application.port.out.BookQueryPort;
import aivle.common.exception.CustomException;
import aivle.infrastructure.adapter.out.projection.repository.BookViewRepository;
import aivle.infrastructure.adapter.out.projection.model.BookView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static aivle.common.response.ErrorCode.NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class BookQueryAdapter implements BookQueryPort {

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
