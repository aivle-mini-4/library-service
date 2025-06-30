package aivle.infrastructure.persistence;

import aivle.infrastructure.projection.BookView;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookViewRepository extends PagingAndSortingRepository<BookView, Long> {
}
