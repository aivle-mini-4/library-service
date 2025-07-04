package aivle.infra.Author;

import aivle.domain.Author.AuthorView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "authorViews",
    path = "authorViews"
)
public interface AuthorViewRepository extends JpaRepository<AuthorView, Long> {
}
