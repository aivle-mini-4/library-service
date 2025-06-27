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
public class QueryFavoriteListViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private QueryFavoriteListRepository queryFavoriteListRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenFavoriteRegistrerd_then_CREATE_1(
        @Payload FavoriteRegistrerd favoriteRegistrerd
    ) {
        try {
            if (!favoriteRegistrerd.validate()) return;

            // view 객체 생성
            QueryFavoriteList queryFavoriteList = new QueryFavoriteList();
            // view 객체에 이벤트의 Value 를 set 함
            queryFavoriteList.setId(favoriteRegistrerd.getId());
            queryFavoriteList.setUserId(favoriteRegistrerd.getUserId());
            queryFavoriteList.setBookId(favoriteRegistrerd.getBookId());
            // view 레파지 토리에 save
            queryFavoriteListRepository.save(queryFavoriteList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenFavoriteRegistrerd_then_UPDATE_1(
        @Payload FavoriteRegistrerd favoriteRegistrerd
    ) {
        try {
            if (!favoriteRegistrerd.validate()) return;
            // view 객체 조회

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenFavoriteDeleted_then_DELETE_1(
        @Payload FavoriteDeleted favoriteDeleted
    ) {
        try {
            if (!favoriteDeleted.validate()) return;
            // view 레파지 토리에 삭제 쿼리
            queryFavoriteListRepository.deleteById(favoriteDeleted.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
