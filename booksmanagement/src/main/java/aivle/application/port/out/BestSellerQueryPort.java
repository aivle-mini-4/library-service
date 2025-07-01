package aivle.application.port.out;

import aivle.infrastructure.adapter.out.projection.model.BestSellerView;

import java.util.List;

public interface BestSellerQueryPort {
    List<BestSellerView> findAll();

    BestSellerView findById(Long bookId);
}
