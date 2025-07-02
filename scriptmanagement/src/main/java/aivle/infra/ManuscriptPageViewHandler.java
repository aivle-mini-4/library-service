package aivle.infra;

import aivle.config.kafka.KafkaProcessor;
import aivle.domain.*;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class ManuscriptPageViewHandler {

    @Autowired
    private ManuscriptPageRepository manuscriptPageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenManuscriptCreated_then_CREATE(
        @Payload ManuscriptCreated manuscriptCreated
    ) {
        try {
            if (!manuscriptCreated.validate()) return;

            // view 객체 생성
            ManuscriptPage manuscriptPage = new ManuscriptPage();

            // view 객체에 이벤트의 Value 를 set 함
            manuscriptPage.setContent(manuscriptCreated.getContent());
            manuscriptPage.setUpdatedAt(manuscriptCreated.getUpdatedAt());
            manuscriptPage.setTitle(manuscriptCreated.getTitle());
            manuscriptPage.setAuthorId(manuscriptCreated.getAuthorId());
            manuscriptPage.setId(manuscriptCreated.getId());

            // view 레파지 토리에 save
            manuscriptPageRepository.save(manuscriptPage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenManuscriptUpdated_then_UPDATE(
        @Payload ManuscriptUpdated manuscriptUpdated
    ) {
        try {
            if (!manuscriptUpdated.validate()) return;

            // view 객체 조회
            Optional<ManuscriptPage> manuscriptPageOptional = manuscriptPageRepository.findById(
                manuscriptUpdated.getId()
            );

            if (manuscriptPageOptional.isPresent()) {
                ManuscriptPage manuscriptPage = manuscriptPageOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                 manuscriptPage.setContent(manuscriptUpdated.getContent());
                manuscriptPage.setUpdatedAt(manuscriptUpdated.getUpdatedAt());
                manuscriptPage.setTitle(manuscriptUpdated.getTitle());
                manuscriptPage.setAuthorId(manuscriptUpdated.getAuthorId());

                // view 레파지 토리에 save
                manuscriptPageRepository.save(manuscriptPage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenManuscriptDeleted_then_DELETE(
        @Payload ManuscriptDeleted manuscriptDeleted
    ) {
        try {
            if (!manuscriptDeleted.validate()) return;
            // view 레파지 토리에 삭제 쿼리
            manuscriptPageRepository.deleteById(manuscriptDeleted.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
