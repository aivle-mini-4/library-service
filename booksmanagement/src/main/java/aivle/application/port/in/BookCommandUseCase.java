package aivle.application.port.in;

import aivle.application.dto.BookRegisterCommand;

public interface BookCommandUseCase {
    void registerBook(BookRegisterCommand cmd);
}
