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
public class SubscribeViewViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private SubscribeViewRepository subscribeViewRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenSignedUp_then_CREATE_1(@Payload SignedUp signedUp) {
        try {
            if (!signedUp.validate()) return;

            // view 객체 생성
            SubscribeView subscribeView = new SubscribeView();
            // view 객체에 이벤트의 Value 를 set 함
            subscribeView.setId(signedUp.getId());
            subscribeView.setIsSubscribed(False);
            // view 레파지 토리에 save
            subscribeViewRepository.save(subscribeView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenSubscribed_then_UPDATE_1(@Payload Subscribed subscribed) {
        try {
            if (!subscribed.validate()) return;
            // view 객체 조회
            Optional<SubscribeView> subscribeViewOptional = subscribeViewRepository.findById(
                subscribed.getId()
            );

            if (subscribeViewOptional.isPresent()) {
                SubscribeView subscribeView = subscribeViewOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                subscribeView.setIsSubscribed(True);
                subscribeView.setUpdatedAt(CURRENT_TIMESTAMP);
                // view 레파지 토리에 save
                subscribeViewRepository.save(subscribeView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenUnSubscribed_then_UPDATE_2(
        @Payload UnSubscribed unSubscribed
    ) {
        try {
            if (!unSubscribed.validate()) return;
            // view 객체 조회
            Optional<SubscribeView> subscribeViewOptional = subscribeViewRepository.findById(
                unSubscribed.getId()
            );

            if (subscribeViewOptional.isPresent()) {
                SubscribeView subscribeView = subscribeViewOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                subscribeView.setIsSubscribed(False);
                subscribeView.setUpdatedAt(CURRENT_TIMESTAMP);
                // view 레파지 토리에 save
                subscribeViewRepository.save(subscribeView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
