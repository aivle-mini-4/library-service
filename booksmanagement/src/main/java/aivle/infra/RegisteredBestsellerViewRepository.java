package aivle.infra;

import aivle.domain.*;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "registeredBestsellerViews",
    path = "registeredBestsellerViews"
)
public interface RegisteredBestsellerViewRepository
    extends PagingAndSortingRepository<RegisteredBestsellerView, Long> {}
