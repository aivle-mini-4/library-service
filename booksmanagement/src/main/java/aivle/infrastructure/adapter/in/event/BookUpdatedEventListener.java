package aivle.infrastructure.adapter.in.event;

import aivle.common.exception.CustomException;
import aivle.domain.event.BookUpdated;
import aivle.infrastructure.adapter.out.projection.model.BestSellerView;
import aivle.infrastructure.adapter.out.projection.model.BookView;
import aivle.infrastructure.adapter.out.projection.repository.BestSellerViewRepository;
import aivle.infrastructure.adapter.out.projection.repository.BookViewRepository;
import aivle.infrastructure.mapper.EventToProjectionMapper;
import aivle.infrastructure.messaging.KafkaProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static aivle.common.response.ErrorCode.INTERNAL_SERVER_ERROR;

@Component

@RequiredArgsConstructor
@Slf4j
public class BookUpdatedEventListener {

    private final BookViewRepository bookViewRepository;
    private final BestSellerViewRepository bestSellerViewRepository;
    private final EventToProjectionMapper eventToProjectionMapper;

    @Transactional
    @StreamListener(
            value = KafkaProcessor.INPUT,
            condition = "headers['type']=='BookUpdated'"
    )
    public void onBookUpdated(@Payload BookUpdated evt) {
        log.debug("Received BookUpdated event: {}", evt);
        try {
            if (!evt.validate()) return;

            // BookView 갱신
            BookView bookView = bookViewRepository.findById(evt.getBookId())
                    .orElseThrow(() -> new RuntimeException("도서 뷰를 찾을 수 없습니다." + evt.getBookId()));
            bookView.updateFromEvent(evt);
            bookViewRepository.save(bookView);

            // BestSellerView 정책 적용
            if (evt.getViews() != null && evt.getViews() >= 10) {
                bestSellerViewRepository.findById(evt.getBookId())
                        .ifPresentOrElse(
                                bestSellerView -> {
                                    bestSellerView.updateFromEvent(evt);
                                    bestSellerViewRepository.save(bestSellerView);
                                },
                                () -> {
                                    BestSellerView bestSellerView = eventToProjectionMapper.toBestSellerView(evt);
                                    bestSellerViewRepository.save(bestSellerView);
                                }
                        );
            }
        } catch (Exception e) {
            throw new CustomException(INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
