package aivle.infra;

import aivle.domain.*;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "adminPages",
    path = "adminPages"
)
public interface AdminPageRepository
    extends PagingAndSortingRepository<AdminPage, Long> {}
