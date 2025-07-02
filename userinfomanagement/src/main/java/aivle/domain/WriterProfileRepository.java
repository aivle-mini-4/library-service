package aivle.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "writerProfiles",
    path = "writerProfiles"
)
public interface WriterProfileRepository
    extends JpaRepository<WriterProfile, Long> {
    
    Optional<WriterProfile> findByAuthorId(Long authorId);
}
