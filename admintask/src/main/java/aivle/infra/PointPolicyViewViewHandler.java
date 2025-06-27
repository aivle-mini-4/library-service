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
public class PointPolicyViewViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private PointPolicyViewRepository pointPolicyViewRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPointPolicyCreated_then_CREATE_1(
        @Payload PointPolicyCreated pointPolicyCreated
    ) {
        try {
            if (!pointPolicyCreated.validate()) return;

            // view 객체 생성
            PointPolicyView pointPolicyView = new PointPolicyView();
            // view 객체에 이벤트의 Value 를 set 함
            pointPolicyView.setId(Long.valueOf(pointPolicyCreated.getId()));
            pointPolicyView.setName(pointPolicyCreated.getName());
            pointPolicyView.setDescription(pointPolicyCreated.getDescription());
            pointPolicyView.setPointType(
                String.valueOf(pointPolicyCreated.getPointType())
            );
            pointPolicyView.setAmount(pointPolicyCreated.getAmount());
            pointPolicyView.setIsActive(pointPolicyCreated.getIsActive());
            pointPolicyView.setCreatedAt(
                Date.valueOf(pointPolicyCreated.getCreatedAt())
            );
            // view 레파지 토리에 save
            pointPolicyViewRepository.save(pointPolicyView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPointPolicyUpdated_then_UPDATE_1(
        @Payload PointPolicyUpdated pointPolicyUpdated
    ) {
        try {
            if (!pointPolicyUpdated.validate()) return;
            // view 객체 조회
            Optional<PointPolicyView> pointPolicyViewOptional = pointPolicyViewRepository.findById(
                pointPolicyUpdated.getId()
            );

            if (pointPolicyViewOptional.isPresent()) {
                PointPolicyView pointPolicyView = pointPolicyViewOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                pointPolicyView.setName(pointPolicyUpdated.getName());
                pointPolicyView.setDescription(
                    pointPolicyUpdated.getDescription()
                );
                pointPolicyView.setPointType(
                    String.valueOf(pointPolicyUpdated.getPointType())
                );
                pointPolicyView.setAmount(pointPolicyUpdated.getAmount());
                pointPolicyView.setIsActive(pointPolicyUpdated.getIsActive());
                pointPolicyView.setUpdatedAt(
                    Date.valueOf(pointPolicyUpdated.getUpdatedAt())
                );
                // view 레파지 토리에 save
                pointPolicyViewRepository.save(pointPolicyView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPointPolicyDeleted_then_DELETE_1(
        @Payload PointPolicyDeleted pointPolicyDeleted
    ) {
        try {
            if (!pointPolicyDeleted.validate()) return;
            // view 레파지 토리에 삭제 쿼리
            pointPolicyViewRepository.deleteById(pointPolicyDeleted.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
