package aivle.application.service;

import aivle.application.dto.BookViewDto;
import aivle.application.mapper.DomainToDtoMapper;
import aivle.application.port.in.BookQueryUseCase;
import aivle.application.port.out.BookViewQueryPort;
import aivle.infrastructure.projection.BookView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookQueryService implements BookQueryUseCase {

    private final BookViewQueryPort bookViewRepository;
    private final DomainToDtoMapper mapper;

    @Override
    public List<BookViewDto> findAllBooks() {
        List<BookView> bookViews = bookViewRepository.findAll();
        return bookViews.stream().map(mapper::toBookViewDto).collect(Collectors.toList());
    }

    @Override
    public BookViewDto findBookById(Long id) {
        BookView bookview = bookViewRepository.findById(id);
        return mapper.toBookViewDto(bookview);
    }
}
