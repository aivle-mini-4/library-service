package aivle.infra;

import aivle.config.kafka.KafkaProcessor;
import aivle.domain.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class RegisteredBookViewViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private RegisteredBookViewRepository registeredBookViewRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenBookRegistered_then_CREATE_1(
        @Payload BookRegistered bookRegistered
    ) {
        try {
            if (!bookRegistered.validate()) return;

            // view 객체 생성
            RegisteredBookView registeredBookView = new RegisteredBookView();
            // view 객체에 이벤트의 Value 를 set 함
            registeredBookView.setId(bookRegistered.getId());
            registeredBookView.setAuthorId(bookRegistered.getAuthorId());
            registeredBookView.setCoverImageUrl(
                bookRegistered.getCoverImageUrl()
            );
            registeredBookView.setTitle(bookRegistered.getTitle());
            registeredBookView.setSummary(bookRegistered.getSummary());
            registeredBookView.setCategory(bookRegistered.getCategory());
            registeredBookView.setPrice(
                String.valueOf(bookRegistered.getPrice())
            );
            registeredBookView.setContent(bookRegistered.getContent());
            registeredBookView.setViews(bookRegistered.getViews());
            // view 레파지 토리에 save
            registeredBookViewRepository.save(registeredBookView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenBookDeleted_then_DELETE_1(
        @Payload BookDeleted bookDeleted
    ) {
        try {
            if (!bookDeleted.validate()) return;
            // view 레파지 토리에 삭제 쿼리
            registeredBookViewRepository.deleteById(bookDeleted.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
