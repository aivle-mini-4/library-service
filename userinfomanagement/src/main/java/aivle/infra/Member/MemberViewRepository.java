package aivle.infra.Member;

import aivle.domain.Member.MemberView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "memberViews",
    path = "memberViews"
)
public interface MemberViewRepository extends JpaRepository<MemberView, Long> {
}
