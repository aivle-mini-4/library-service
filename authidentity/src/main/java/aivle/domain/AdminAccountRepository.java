package aivle.domain;

import aivle.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "adminAccounts",
    path = "adminAccounts"
)
public interface AdminAccountRepository
    extends PagingAndSortingRepository<AdminAccount, Long> {}
