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
    WriterProfileRepository writerProfileRepository;

    @Autowired
    MemberProfileRepository memberProfileRepository;

    @Autowired
    AdminProfileRepository adminProfileRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='AuthorApproved'"
    )
    public void wheneverAuthorApproved_NotifyAuthorOnRegistrationApproval(
        @Payload AuthorApproved authorApproved
    ) {
        AuthorApproved event = authorApproved;
        System.out.println(
            "\n\n##### listener NotifyAuthorOnRegistrationApproval : " +
            authorApproved +
            "\n\n"
        );

        // Sample Logic //
        WriterProfile.notifyAuthorOnRegistrationApproval(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='AuthorRejected'"
    )
    public void wheneverAuthorRejected_NotifyAuthorOnRegistrationRejection(
        @Payload AuthorRejected authorRejected
    ) {
        AuthorRejected event = authorRejected;
        System.out.println(
            "\n\n##### listener NotifyAuthorOnRegistrationRejection : " +
            authorRejected +
            "\n\n"
        );

        // Sample Logic //
        WriterProfile.notifyAuthorOnRegistrationRejection(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='AuthorApproved'"
    )
    public void wheneverAuthorApproved_CreateWriterProfile(
        @Payload AuthorApproved authorApproved
    ) {
        AuthorApproved event = authorApproved;
        System.out.println(
            "\n\n##### listener CreateWriterProfile : " +
            authorApproved +
            "\n\n"
        );

        // Sample Logic //
        WriterProfile.createWriterProfile(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SignedUp'"
    )
    public void wheneverSignedUp_CreateAdminProfile(
        @Payload SignedUp signedUp
    ) {
        SignedUp event = signedUp;
        System.out.println(
            "\n\n##### listener CreateAdminProfile : " + signedUp + "\n\n"
        );

        // Sample Logic //
        AdminProfile.createAdminProfile(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SignedUp'"
    )
    public void wheneverSignedUp_CreateMemberProfile(
        @Payload SignedUp signedUp
    ) {
        SignedUp event = signedUp;
        System.out.println(
            "\n\n##### listener CreateMemberProfile : " + signedUp + "\n\n"
        );

        // Sample Logic //
        MemberProfile.createMemberProfile(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
