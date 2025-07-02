package aivle.infra;

import aivle.config.kafka.KafkaProcessor;
import aivle.domain.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    PointRepository pointRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    // 테스트
    // @StreamListener(KafkaProcessor.INPUT)
    // public void onEvent(@Payload String eventString) {
    //     try {
    //         ObjectMapper mapper = new ObjectMapper();
    //         mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    //         Map<String, Object> eventMap = mapper.readValue(eventString, Map.class);

    //         String eventType = (String) eventMap.get("eventType");
    //         System.out.println("🔔 수신된 이벤트 타입: " + eventType);

    //         if ("SignedUp".equals(eventType)) {
    //             SignedUp signedUp = mapper.convertValue(eventMap, SignedUp.class);
    //             System.out.println("✅ SignedUp 처리 시작: " + signedUp);
    //             Point.grantPoints(signedUp);
    //         }

    //         // 향후 다른 이벤트 타입도 여기에 if 추가하면 됨
    //         if ("PointPolicyCreated".equals(eventType)) {
    //             PointPolicyCreated policyCreated = mapper.convertValue(eventMap, PointPolicyCreated.class);
    //             System.out.println("📌 정책 생성 이벤트 처리됨: " + policyCreated);
    //             Point.pointManagement(policyCreated);
    //         }

    //     } catch (Exception e) {
    //         System.out.println("❌ 이벤트 처리 중 오류 발생: " + e.getMessage());
    //     }
    // }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SignedUp'"
    )
    public void wheneverSignedUp_GrantPoints(@Payload SignedUp signedUp) {
        SignedUp event = signedUp;
        System.out.println(
            "\n\n##### listener GrantPoints : " + signedUp + "\n\n"
        );

        // Sample Logic //
        Point.grantPoints(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='BookSubscribed'"
    )
    public void wheneverBookSubscribed_PointDeduction(
        @Payload BookSubscribed bookSubscribed
    ) {
        BookSubscribed event = bookSubscribed;
        System.out.println(
            "\n\n##### listener PointDeduction : " + bookSubscribed + "\n\n"
        );

        // Comments //
        //Whenever 도서열람됨 Then 포인트사용

        // Sample Logic //
        Point.pointDeduction(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PointPolicyCreated'"
    )
    public void wheneverPointPolicyCreated_PointManagement(
        @Payload PointPolicyCreated pointPolicyCreated
    ) {
        PointPolicyCreated event = pointPolicyCreated;
        System.out.println(
            "\n\n##### listener PointManagement : " +
            pointPolicyCreated +
            "\n\n"
        );

        // Sample Logic //
        Point.pointManagement(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PointPolicyUpdated'"
    )
    public void wheneverPointPolicyUpdated_PointManagement(
        @Payload PointPolicyUpdated pointPolicyUpdated
    ) {
        PointPolicyUpdated event = pointPolicyUpdated;
        System.out.println(
            "\n\n##### listener PointManagement : " +
            pointPolicyUpdated +
            "\n\n"
        );

        // Sample Logic //
        Point.pointManagement(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PointPolicyDeleted'"
    )
    public void wheneverPointPolicyDeleted_PointManagement(
        @Payload PointPolicyDeleted pointPolicyDeleted
    ) {
        PointPolicyDeleted event = pointPolicyDeleted;
        System.out.println(
            "\n\n##### listener PointManagement : " +
            pointPolicyDeleted +
            "\n\n"
        );

        // Sample Logic //
        Point.pointManagement(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
