package aivle.infrastructure.adapter.in.rest;

import aivle.application.dto.BookDeleteCommand;
import aivle.application.port.in.BookCommandUseCase;
import aivle.common.response.ApiResponse;
import aivle.common.response.ResponseFactory;
import aivle.common.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookCommandUseCase bookCommandUseCase;
    private final ResponseFactory responseFactory;

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable Long id) {
        bookCommandUseCase.deleteBook(BookDeleteCommand.builder().bookId(id).build());
        return responseFactory.success(SuccessCode.OK);
    }
}
