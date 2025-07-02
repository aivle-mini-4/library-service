package aivle.infrastructure.adapter.in.rest;//package aivle.presentation;

import aivle.application.dto.BestSellerViewDto;
import aivle.application.port.in.BestSellerQueryUseCase;
import aivle.common.response.ApiResponse;
import aivle.common.response.ResponseFactory;
import aivle.common.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bestseller-views")
@RequiredArgsConstructor
public class BestSellerViewController {

    private final BestSellerQueryUseCase bestSellerQueryUseCase;
    private final ResponseFactory responseFactory;

    @GetMapping
    public ResponseEntity<ApiResponse<List<BestSellerViewDto>>> getAllBestSellers() {
        List<BestSellerViewDto> bestSellerViewDtos = bestSellerQueryUseCase.findAllBestSellers();
        return responseFactory.success(SuccessCode.OK, bestSellerViewDtos);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<ApiResponse<BestSellerViewDto>> getBook(@PathVariable Long bookId) {
        BestSellerViewDto bestSellerViewDto = bestSellerQueryUseCase.findBestSellerById(bookId);
        return responseFactory.success(SuccessCode.OK, bestSellerViewDto);
    }
}
