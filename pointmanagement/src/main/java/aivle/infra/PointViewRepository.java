package aivle.infra;

import aivle.domain.*;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "pointViews",
    path = "pointViews"
)
public interface PointViewRepository
    extends PagingAndSortingRepository<PointView, Long> {
    List<PointView> findByUserId(Long userId);
}
