package aivle.application.service;

import aivle.application.dto.BookViewDto;
import aivle.application.mapper.BookMapper;
import aivle.application.port.in.BookQueryUseCase;
import aivle.application.port.out.BookQueryPort;
import aivle.infrastructure.adapter.out.projection.model.BookView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookQueryService implements BookQueryUseCase {

    private final BookQueryPort bookQueryPort;
    private final BookMapper bookMapper;

    @Override
    public List<BookViewDto> findAllBooks() {
        List<BookView> bookViews = bookQueryPort.findAll();
        return bookViews.stream().map(bookMapper::toBookViewDto).collect(Collectors.toList());
    }

    @Override
    public BookViewDto findBookById(Long id) {
        BookView bookview = bookQueryPort.findById(id);
        return bookMapper.toBookViewDto(bookview);
    }
}
