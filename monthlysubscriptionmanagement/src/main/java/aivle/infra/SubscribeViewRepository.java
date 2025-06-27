package aivle.infra;

import aivle.domain.*;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "subscribeViews",
    path = "subscribeViews"
)
public interface SubscribeViewRepository
    extends PagingAndSortingRepository<SubscribeView, Long> {}
