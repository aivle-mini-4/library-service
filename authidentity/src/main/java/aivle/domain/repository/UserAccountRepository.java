package aivle.domain.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import aivle.domain.entity.UserAccount;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "userAccounts",
    path = "userAccounts"
)
public interface UserAccountRepository
    extends PagingAndSortingRepository<UserAccount, Long> {}
