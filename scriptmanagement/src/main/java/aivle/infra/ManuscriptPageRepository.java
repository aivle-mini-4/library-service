package aivle.infra;

import aivle.domain.*;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "manuscriptPages",
    path = "manuscriptPages"
)
public interface ManuscriptPageRepository
    extends PagingAndSortingRepository<ManuscriptPage, Long> {}
