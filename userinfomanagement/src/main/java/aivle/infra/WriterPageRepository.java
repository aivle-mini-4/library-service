package aivle.infra;

import aivle.domain.*;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "writerPages",
    path = "writerPages"
)
public interface WriterPageRepository
    extends PagingAndSortingRepository<WriterPage, Long> {
    
    Optional<WriterPage> findById(Long id);
}
