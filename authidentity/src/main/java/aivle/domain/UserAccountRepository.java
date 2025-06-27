package aivle.domain;

import aivle.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "userAccounts",
    path = "userAccounts"
)
public interface UserAccountRepository
    extends PagingAndSortingRepository<UserAccount, Long> {}
