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
public class UserPageViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private UserPageRepository userPageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenMemberProfileCreated_then_CREATE_1(
        @Payload MemberProfileCreated memberProfileCreated
    ) {
        try {
            if (!memberProfileCreated.validate()) return;

            // view 객체 생성
            UserPage userPage = new UserPage();
            // view 객체에 이벤트의 Value 를 set 함
            userPage.setUserId(memberProfileCreated.getUserId());
            userPage.setName(memberProfileCreated.getName());
            userPage.setEmail(memberProfileCreated.getEmail());
            userPage.setBasicInfo(memberProfileCreated.getBasicInformation());
            userPage.setJoinedAt(CURRENT_TIMESTAMP);
            // view 레파지 토리에 save
            userPageRepository.save(userPage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenMemberProfileUpdated_then_UPDATE_1(
        @Payload MemberProfileUpdated memberProfileUpdated
    ) {
        try {
            if (!memberProfileUpdated.validate()) return;
            // view 객체 조회
            Optional<UserPage> userPageOptional = userPageRepository.findByUserId(
                memberProfileUpdated.getUserId()
            );

            if (userPageOptional.isPresent()) {
                UserPage userPage = userPageOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                userPage.setName(memberProfileUpdated.getName());
                userPage.setEmail(memberProfileUpdated.getEmail());
                userPage.setBasicInfo(
                    memberProfileUpdated.getBasicInformation()
                );
                userPage.setLastUpdatedAt(CURRENT_TIMESTAMP);
                // view 레파지 토리에 save
                userPageRepository.save(userPage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
