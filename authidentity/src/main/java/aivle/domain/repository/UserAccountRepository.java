package aivle.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import aivle.domain.entity.UserAccount;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "userAccounts",
    path = "userAccounts"
)
public interface UserAccountRepository
    extends JpaRepository<UserAccount, Long> {
    
    Optional<UserAccount> findByEmail(String email);
}
