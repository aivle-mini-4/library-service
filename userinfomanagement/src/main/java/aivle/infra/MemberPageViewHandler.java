package aivle.infra;

import aivle.config.kafka.KafkaProcessor;
import aivle.domain.*;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class MemberPageViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private MemberPageRepository memberPageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenMemberProfileCreated_then_CREATE_1(
        @Payload MemberProfileCreated memberProfileCreated
    ) {
        try {
            if (!memberProfileCreated.validate()) return;

            // view 객체 생성
            MemberPage memberPage = new MemberPage();
            // view 객체에 이벤트의 Value 를 set 함
            memberPage.setId(memberProfileCreated.getId());
            memberPage.setName(memberProfileCreated.getName());
            memberPage.setEmail(memberProfileCreated.getEmail());
            memberPage.setBasicInfo(memberProfileCreated.getBasicInformation());
            memberPage.setJoinedAt(new java.util.Date());
            // view 레파지 토리에 save
            memberPageRepository.save(memberPage);
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
            Optional<MemberPage> memberPageOptional = memberPageRepository.findById(
                memberProfileUpdated.getId()
            );

            if (memberPageOptional.isPresent()) {
                MemberPage memberPage = memberPageOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                memberPage.setName(memberProfileUpdated.getName());
                memberPage.setEmail(memberProfileUpdated.getEmail());
                memberPage.setBasicInfo(
                    memberProfileUpdated.getBasicInformation()
                );
                memberPage.setLastUpdatedAt(new java.util.Date());
                // view 레파지 토리에 save
                memberPageRepository.save(memberPage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
