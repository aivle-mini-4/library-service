package aivle.domain;

import aivle.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "authorAccounts",
    path = "authorAccounts"
)
public interface AuthorAccountRepository
    extends PagingAndSortingRepository<AuthorAccount, Long> {}
