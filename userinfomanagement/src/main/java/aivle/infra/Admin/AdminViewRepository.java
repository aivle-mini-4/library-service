package aivle.infra.Admin;

import aivle.domain.Admin.AdminView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "adminViews",
    path = "adminViews"
)
public interface AdminViewRepository extends JpaRepository<AdminView, Long> {
}
