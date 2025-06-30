package aivle.infra.persistence;

import aivle.domain.model.RegisteredBookView;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "registeredBookViews",
    path = "registeredBookViews"
)
public interface RegisteredBookViewRepository
    extends PagingAndSortingRepository<RegisteredBookView, Long> {}
