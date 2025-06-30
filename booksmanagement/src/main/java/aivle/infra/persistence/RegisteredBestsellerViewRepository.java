package aivle.infra.persistence;

import aivle.domain.model.RegisteredBestsellerView;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "registeredBestsellerViews",
    path = "registeredBestsellerViews"
)
public interface RegisteredBestsellerViewRepository
    extends PagingAndSortingRepository<RegisteredBestsellerView, Long> {}
