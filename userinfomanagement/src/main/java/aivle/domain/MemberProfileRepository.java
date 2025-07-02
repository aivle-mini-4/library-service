package aivle.domain;

import aivle.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "memberProfiles",
    path = "memberProfiles"
)
public interface MemberProfileRepository
    extends PagingAndSortingRepository<MemberProfile, Long> {}
