package aivle.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import aivle.domain.entity.AdminAccount;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "adminAccounts",
    path = "adminAccounts"
)
public interface AdminAccountRepository
    extends JpaRepository<AdminAccount, Long> {
    
    Optional<AdminAccount> findByEmail(String email);
}
