package aivle.domain;

import aivle.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "aiServices",
    path = "aiServices"
)
public interface AiServiceRepository
    extends PagingAndSortingRepository<AiService, Long> {}
