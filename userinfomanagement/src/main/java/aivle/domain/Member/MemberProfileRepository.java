package aivle.domain.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "memberProfiles", path = "memberProfiles")
public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long> {}