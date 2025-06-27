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
public class AdminPageViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private AdminPageRepository adminPageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenAdminProfileCreated_then_CREATE_1(
        @Payload AdminProfileCreated adminProfileCreated
    ) {
        try {
            if (!adminProfileCreated.validate()) return;

            // view 객체 생성
            AdminPage adminPage = new AdminPage();
            // view 객체에 이벤트의 Value 를 set 함
            adminPage.setUserId(adminProfileCreated.getUserId());
            adminPage.setName(adminProfileCreated.getName());
            // view 레파지 토리에 save
            adminPageRepository.save(adminPage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
