package aivle.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface StoredPointPolicyRepository extends JpaRepository<StoredPointPolicy, Long> {

    List<StoredPointPolicy> findByPointType(PointType pointType);
}
