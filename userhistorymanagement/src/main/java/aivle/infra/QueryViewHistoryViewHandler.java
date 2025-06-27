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
public class QueryViewHistoryViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private QueryViewHistoryRepository queryViewHistoryRepository;
    //>>> DDD / CQRS
}
