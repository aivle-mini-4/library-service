package aivle.infra;

import aivle.domain.*;
import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "memberPages", path = "memberPages")
public interface MemberPageRepository
    extends PagingAndSortingRepository<MemberPage, Long> {
    
    Optional<MemberPage> findById(Long id);
}
