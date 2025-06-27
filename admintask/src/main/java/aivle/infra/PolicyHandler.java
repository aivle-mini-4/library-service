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
    AuthorapprovalRepository authorapprovalRepository;

    @Autowired
    PointpolicyRepository pointpolicyRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='AuthorRegistrationRequested'"
    )
    public void wheneverAuthorRegistrationRequested_AuthorRouting(
        @Payload AuthorRegistrationRequested authorRegistrationRequested
    ) {
        AuthorRegistrationRequested event = authorRegistrationRequested;
        System.out.println(
            "\n\n##### listener AuthorRouting : " +
            authorRegistrationRequested +
            "\n\n"
        );

        // Sample Logic //
        Authorapproval.authorRouting(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
