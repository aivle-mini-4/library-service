package aivle.infrastructure.adapter.out.persistence;

import aivle.domain.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
