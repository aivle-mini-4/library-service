package aivle.infra;

import aivle.config.kafka.KafkaProcessor;
import aivle.domain.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.Date;

import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    SubscribeRepository subscribeRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverSignedUp_CreateInitialSubscription(@Payload SignedUp signedUp) {
        if (!signedUp.validate()) return;

        System.out.println("회원가입 이벤트 감지: " + signedUp.toJson());

        Subscribe subscribe = new Subscribe();
        subscribe.setUserId(signedUp.getId());
        // subscribe.setName(signedUp.getEmail());
        subscribe.setIsSubscribed(false);        // 기본값: 구독 안 함
        subscribe.setUpdatedAt(LocalDateTime.now());

        subscribeRepository.save(subscribe);
    }
}
//>>> Clean Arch / Inbound Adaptor
