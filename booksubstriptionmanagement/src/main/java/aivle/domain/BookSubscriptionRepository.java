package aivle.domain;

import aivle.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.Optional;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "bookSubscriptions",
    path = "bookSubscriptions"
)
public interface BookSubscriptionRepository
    extends PagingAndSortingRepository<BookSubscription, Long> {
        Optional<BookSubscription> findByUserIdAndBookId(Long userId, Long bookId);
}
