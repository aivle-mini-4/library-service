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

    @StreamListener(KafkaProcessor.INPUT)
    public void whenViewHistoryRegistered_then_CREATE_1(
        @Payload ViewHistoryRegistered viewHistoryRegistered
    ) {
        try {
            if (!viewHistoryRegistered.validate()) return;

            // view 객체 생성
            QueryViewHistory queryViewHistory = new QueryViewHistory();

            // view 객체에 이벤트의 Value 를 set 함
            queryViewHistory.setId(viewHistoryRegistered.getId());
            queryViewHistory.setBookId(viewHistoryRegistered.getBookId());
            queryViewHistory.setUserId(viewHistoryRegistered.getUserId());
            
            // view 레파지 토리에 save
            queryViewHistoryRepository.save(queryViewHistory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
