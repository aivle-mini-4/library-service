package aivle.application.port.in;

import aivle.application.dto.BestSellerViewDto;

import java.util.List;

public interface BestSellerQueryUseCase {
    List<BestSellerViewDto> findAllBestSellers();

    BestSellerViewDto findBestSellerById(Long bookId);
}
