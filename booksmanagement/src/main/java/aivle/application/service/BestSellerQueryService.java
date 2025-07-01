package aivle.application.service;

import aivle.application.dto.BestSellerViewDto;
import aivle.application.mapper.BestSellerMapper;
import aivle.application.port.in.BestSellerQueryUseCase;
import aivle.application.port.out.BestSellerQueryPort;
import aivle.infrastructure.adapter.out.projection.model.BestSellerView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BestSellerQueryService implements BestSellerQueryUseCase {

    private final BestSellerQueryPort bestSellerQueryPort;
    private final BestSellerMapper bestSellerMapper;

    @Override
    public List<BestSellerViewDto> findAllBestSellers() {
        List<BestSellerView> bestSellerViews = bestSellerQueryPort.findAll();
        return bestSellerViews.stream().map(bestSellerMapper::toBestSellerViewDto).collect(Collectors.toList());
    }

    @Override
    public BestSellerViewDto findBestSellerById(Long bookId) {
        BestSellerView bestSellerView = bestSellerQueryPort.findById(bookId);
        return bestSellerMapper.toBestSellerViewDto(bestSellerView);
    }
}
