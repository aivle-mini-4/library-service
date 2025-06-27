package aivle.domain;

import aivle.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "bookSubscriptions",
    path = "bookSubscriptions"
)
public interface BookSubscriptionRepository
    extends PagingAndSortingRepository<BookSubscription, Long> {}
