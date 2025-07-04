package aivle.domain.Admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "adminProfiles",
    path = "adminProfiles"
)
public interface AdminProfileRepository
    extends JpaRepository<AdminProfile, Long> {}
