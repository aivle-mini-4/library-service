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
public class WriterPageViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private WriterPageRepository writerPageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenWriterProfileCreated_then_CREATE_1(
        @Payload WriterProfileCreated writerProfileCreated
    ) {
        try {
            if (!writerProfileCreated.validate()) return;

            // view 객체 생성
            WriterPage writerPage = new WriterPage();
            // view 객체에 이벤트의 Value 를 set 함
            writerPage.setUserId(writerProfileCreated.getUserId());
            writerPage.setName(writerProfileCreated.getName());
            writerPage.setEmail(writerProfileCreated.getEmail());
            writerPage.setBasicInformation(
                writerProfileCreated.getBasicInformation()
            );
            writerPage.setSelfInformation(
                writerProfileCreated.getSelfIntroduction()
            );
            writerPage.setPortfolio(writerProfileCreated.getPortfolio());
            // view 레파지 토리에 save
            writerPageRepository.save(writerPage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenWriterProfileUpdated_then_UPDATE_1(
        @Payload WriterProfileUpdated writerProfileUpdated
    ) {
        try {
            if (!writerProfileUpdated.validate()) return;
            // view 객체 조회
            Optional<WriterPage> writerPageOptional = writerPageRepository.findByUserId(
                writerProfileUpdated.getUserId()
            );

            if (writerPageOptional.isPresent()) {
                WriterPage writerPage = writerPageOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                writerPage.setName(writerProfileUpdated.getName());
                writerPage.setEmail(writerProfileUpdated.getEmail());
                writerPage.setBasicInformation(
                    writerProfileUpdated.getBasicInformation()
                );
                writerPage.setSelfInformation(
                    writerProfileUpdated.getSelfIntroduction()
                );
                writerPage.setPortfolio(writerProfileUpdated.getPortfolio());
                // view 레파지 토리에 save
                writerPageRepository.save(writerPage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
