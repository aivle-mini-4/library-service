package aivle.domain.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import aivle.domain.entity.AdminAccount;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "adminAccounts",
    path = "adminAccounts"
)
public interface AdminAccountRepository
    extends PagingAndSortingRepository<AdminAccount, Long> {}
