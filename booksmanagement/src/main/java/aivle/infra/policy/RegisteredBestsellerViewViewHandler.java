package aivle.infra.policy;

import aivle.infra.messaging.KafkaProcessor;

import aivle.domain.event.BestsellerRegistered;
import aivle.domain.model.RegisteredBestsellerView;
import aivle.infra.persistence.RegisteredBestsellerViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class RegisteredBestsellerViewViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private RegisteredBestsellerViewRepository registeredBestsellerViewRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenBestsellerRegistered_then_CREATE_1(
        @Payload BestsellerRegistered bestsellerRegistered
    ) {
        try {
            if (!bestsellerRegistered.validate()) return;

            // view 객체 생성
            RegisteredBestsellerView registeredBestsellerView = new RegisteredBestsellerView();
            // view 객체에 이벤트의 Value 를 set 함
            registeredBestsellerView.setId(bestsellerRegistered.getId());
            registeredBestsellerView.setAuthorId(
                bestsellerRegistered.getAuthorId()
            );
            registeredBestsellerView.setCoverImageUrl(
                bestsellerRegistered.getCoverImageUrl()
            );
            registeredBestsellerView.setTitle(bestsellerRegistered.getTitle());
            registeredBestsellerView.setSummary(
                bestsellerRegistered.getSummary()
            );
            registeredBestsellerView.setCategory(
                bestsellerRegistered.getCategory()
            );
            registeredBestsellerView.setPrice(bestsellerRegistered.getPrice());
            registeredBestsellerView.setContent(
                bestsellerRegistered.getContent()
            );
            registeredBestsellerView.setViews(bestsellerRegistered.getViews());
            // view 레파지 토리에 save
            registeredBestsellerViewRepository.save(registeredBestsellerView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
