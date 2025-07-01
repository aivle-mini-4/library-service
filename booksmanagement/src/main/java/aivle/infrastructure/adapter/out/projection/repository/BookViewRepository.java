package aivle.infrastructure.adapter.out.projection.repository;

import aivle.infrastructure.adapter.out.projection.model.BookView;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookViewRepository extends PagingAndSortingRepository<BookView, Long> {
}
