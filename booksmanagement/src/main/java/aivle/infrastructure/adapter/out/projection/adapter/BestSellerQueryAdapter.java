package aivle.infrastructure.adapter.out.projection.adapter;

import aivle.application.port.out.BestSellerQueryPort;
import aivle.common.exception.CustomException;
import aivle.infrastructure.adapter.out.projection.repository.BestSellerViewRepository;
import aivle.infrastructure.adapter.out.projection.model.BestSellerView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static aivle.common.response.ErrorCode.NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class BestSellerQueryAdapter implements BestSellerQueryPort {

    private final BestSellerViewRepository repo;

    @Override
    public List<BestSellerView> findAll() {
        return (List<BestSellerView>) repo.findAll();
    }

    @Override
    public BestSellerView findById(Long bookId) {
        return repo.findById(bookId).orElseThrow(() -> new CustomException(NOT_FOUND, "베스트셀러를 찾을 수 없습니다."));
    }
}
