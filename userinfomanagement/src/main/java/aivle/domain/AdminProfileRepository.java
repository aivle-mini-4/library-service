package aivle.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "adminProfiles",
    path = "adminProfiles"
)
public interface AdminProfileRepository
    extends PagingAndSortingRepository<AdminProfile, Long> {}
