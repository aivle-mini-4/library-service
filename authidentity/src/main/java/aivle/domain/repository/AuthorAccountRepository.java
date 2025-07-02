package aivle.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import aivle.domain.entity.AuthorAccount;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "authorAccounts",
    path = "authorAccounts"
)
public interface AuthorAccountRepository
    extends JpaRepository<AuthorAccount, Long> {
    
    Optional<AuthorAccount> findByEmail(String email);
}
