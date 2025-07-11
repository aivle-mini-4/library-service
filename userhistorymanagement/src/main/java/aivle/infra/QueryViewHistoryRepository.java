package aivle.infra;

import aivle.domain.*;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "queryViewHistories",
    path = "queryViewHistories"
)
public interface QueryViewHistoryRepository
    extends PagingAndSortingRepository<QueryViewHistory, Long> {
        List<QueryViewHistory> findByUserId(Long userId);
}
