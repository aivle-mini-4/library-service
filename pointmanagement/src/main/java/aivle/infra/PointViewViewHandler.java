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
public class PointViewViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private PointViewRepository pointViewRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPointsGranted_then_CREATE_1(
        @Payload PointsGranted pointsGranted
    ) {
        try {
            if (!pointsGranted.validate()) return;

            // view 객체 생성
            PointView pointView = new PointView();
            // view 객체에 이벤트의 Value 를 set 함
            pointView.setId(pointsGranted.getId());
            pointView.setUserId(pointsGranted.getUserId());
            pointView.setPoints(pointsGranted.getPoints());
            pointView.setHistory(String.valueOf(pointsGranted.getHistory()));
            pointView.setUserId(pointsGranted.getUserId());
            pointView.setPoints(pointsGranted.getPoints());
            pointView.setHistory(String.valueOf(pointsGranted.getHistory()));
            // view 레파지 토리에 save
            pointViewRepository.save(pointView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPointUsed_then_UPDATE_1(@Payload PointUsed pointUsed) {
        try {
            if (!pointUsed.validate()) return;
            // view 객체 조회

            List<PointView> pointViewList = pointViewRepository.findByUserId(
                pointUsed.getUserId()
            );
            for (PointView pointView : pointViewList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                pointView.setPoints(pointUsed.getPoints());
                pointView.setHistory(String.valueOf(pointUsed.getHistory()));
                pointView.setHistory(String.valueOf(pointUsed.getHistory()));
                // view 레파지 토리에 save
                pointViewRepository.save(pointView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPointExpired_then_UPDATE_2(
        @Payload PointExpired pointExpired
    ) {
        try {
            if (!pointExpired.validate()) return;
            // view 객체 조회

            List<PointView> pointViewList = pointViewRepository.findByUserId(
                pointExpired.getUserId()
            );
            for (PointView pointView : pointViewList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                pointView.setPoints(0);
                pointView.setHistory(String.valueOf(pointExpired.getHistory()));
                pointView.setHistory(String.valueOf(pointExpired.getHistory()));
                // view 레파지 토리에 save
                pointViewRepository.save(pointView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
