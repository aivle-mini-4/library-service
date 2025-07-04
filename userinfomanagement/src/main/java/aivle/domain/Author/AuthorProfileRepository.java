package aivle.domain.Author;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "authorProfiles", path = "authorProfiles")
public interface AuthorProfileRepository extends JpaRepository<AuthorProfile, Long> {}
