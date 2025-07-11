package aivle.domain;

import aivle.domain.*;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "pointpolicies",
    path = "pointpolicies"
)
public interface PointpolicyRepository
    extends PagingAndSortingRepository<Pointpolicy, Long> {}
