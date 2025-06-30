package aivle.domain.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import aivle.domain.entity.AuthorAccount;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "authorAccounts",
    path = "authorAccounts"
)
public interface AuthorAccountRepository
    extends PagingAndSortingRepository<AuthorAccount, Long> {
    
    Optional<AuthorAccount> findByEmail(String email);
}
