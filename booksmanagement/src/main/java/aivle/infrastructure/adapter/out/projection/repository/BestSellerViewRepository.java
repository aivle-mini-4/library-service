package aivle.infrastructure.adapter.out.projection.repository;

import aivle.infrastructure.adapter.out.projection.model.BestSellerView;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BestSellerViewRepository extends PagingAndSortingRepository<BestSellerView, Long> {
}
