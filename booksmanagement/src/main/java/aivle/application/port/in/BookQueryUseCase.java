package aivle.application.port.in;

import aivle.application.dto.BookViewDto;

import java.util.List;

public interface BookQueryUseCase {
    List<BookViewDto> findAllBooks();

    BookViewDto findBookById(Long bookId);
}
