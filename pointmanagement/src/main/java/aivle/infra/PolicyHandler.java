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
        condition = "headers['type']=='ViewHistoryRegistered'"
    )
    public void wheneverViewHistoryRegistered_PointDeduction(
        @Payload ViewHistoryRegistered viewHistoryRegistered
    ) {
        ViewHistoryRegistered event = viewHistoryRegistered;
        System.out.println(
            "\n\n##### listener PointDeduction : " +
            viewHistoryRegistered +
            "\n\n"
        );

        // Comments //
        //Whenever 도서열람됨 Then 포인트사용

        // Sample Logic //
        Point.pointDeduction(event);
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
