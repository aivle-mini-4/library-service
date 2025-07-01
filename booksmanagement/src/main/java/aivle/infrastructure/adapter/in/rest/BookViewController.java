package aivle.infrastructure.adapter.in.rest;//package aivle.presentation;

import aivle.application.dto.BookViewDto;
import aivle.application.port.in.BookQueryUseCase;
import aivle.common.response.ApiResponse;
import aivle.common.response.ResponseFactory;
import aivle.common.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books-view")
@RequiredArgsConstructor
public class BookViewController {

    private final BookQueryUseCase bookViewUseCase;
    private final ResponseFactory responseFactory;

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookViewDto>>> getAllBooks() {
        List<BookViewDto> bookViewDtos = bookViewUseCase.findAllBooks();
        return responseFactory.success(SuccessCode.OK, bookViewDtos);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<ApiResponse<BookViewDto>> getBook(@PathVariable Long bookId) {
        BookViewDto bookViewDto = bookViewUseCase.findBookById(bookId);
        return responseFactory.success(SuccessCode.OK, bookViewDto);
    }
}
