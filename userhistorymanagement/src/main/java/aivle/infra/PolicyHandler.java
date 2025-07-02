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
    ViewHistoryRepository viewHistoryRepository;

    @Autowired
    FavoriteRepository favoriteRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='MonthlyBookSubscribed'"
    )
    public void wheneverMonthlyBookSubscribed_RegisterViewHistory(
        @Payload MonthlyBookSubscribed MonthlyBookSubscribed
    ) {
        MonthlyBookSubscribed event = MonthlyBookSubscribed;
        System.out.println(
            "\n\n##### listener RegisterViewHistory : " +
            MonthlyBookSubscribed +
            "\n\n"
        );

        // Sample Logic //
        ViewHistory.registerViewHistory(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PointUsed'"
    )
    public void wheneverPointUsed_RegisterViewHistory(
        @Payload PointUsed pointUsed
    ) {
        PointUsed event = pointUsed;
        System.out.println(
            "\n\n##### listener RegisterViewHistory : " + pointUsed + "\n\n"
        );

        // Sample Logic //
        ViewHistory.registerViewHistory(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
