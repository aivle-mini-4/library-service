package aivle.application.port.in;

import aivle.application.dto.BookDeleteCommand;
import aivle.application.dto.BookRegisterCommand;

public interface BookCommandUseCase {
    void registerBook(BookRegisterCommand cmd);

    void deleteBook(BookDeleteCommand cmd);

    void increaseViewCount(Long bookId);
}
