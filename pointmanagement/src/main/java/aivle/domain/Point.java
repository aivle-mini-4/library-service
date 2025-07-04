package aivle.domain;

import aivle.PointmanagementApplication;
import aivle.domain.PointExpired;
import aivle.domain.PointUsed;
import aivle.domain.PointsGranted;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Point_table")
@Data
//<<< DDD / Aggregate Root
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId; // 유저식별 userId로 해야할 것 같아서
    private String name;

    private Integer points;

    private LocalDateTime history;

    public static PointRepository repository() {
        PointRepository pointRepository = PointmanagementApplication.applicationContext.getBean(
            PointRepository.class
        );
        return pointRepository;
    }

    public static StoredPointPolicyRepository storedPointPolicyRepository() {
        return PointmanagementApplication.applicationContext.getBean(StoredPointPolicyRepository.class);
    }


    //<<< Clean Arch / Port Method
    public static void grantPoints(SignedUp signedUp) {

        // admin확인
        if("admin".equals(signedUp.getRoles())){
            System.out.println("⚠ 관리자 유저입니다(포인트x): " + signedUp.getId());
            return;
        }

        if (repository().findByUserId(signedUp.getId()).isPresent()) {
            System.out.println("⚠ 이미 포인트가 지급된 유저입니다: " + signedUp.getId());
            return;
        }

        Point point = new Point();
        point.setUserId(signedUp.getId());

        int totalPoints = 0;

        List<StoredPointPolicy> policies = storedPointPolicyRepository().findByPointType(PointType.ACCUMULATION); 

        for (StoredPointPolicy policy : policies) {
            if (!Boolean.TRUE.equals(policy.getIsActive())) continue;

            String policyName = policy.getName();

            if ("기본포인트".equals(policyName)) {
                totalPoints += policy.getAmount();
            } else if ("kt유저보너스포인트".equals(policyName)) {
                if (isKt(signedUp.getEmail())) {
                    totalPoints += policy.getAmount();
                }
            }    
        }

        point.setPoints(totalPoints);
        point.setHistory(LocalDateTime.now());
        repository().save(point);

        PointsGranted pointsGranted = new PointsGranted(point);
        pointsGranted.publishAfterCommit();
    }
    // kt 회원인지 확인하는 함수 (수정필요)
    private static boolean isKt(String email) {
        return email.endsWith("@kt.com");
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void pointDeduction(BookSubscribed bookSubscribed) {

        repository().findByUserId(bookSubscribed.getUserId()).ifPresent(point->{
            
            int price = bookSubscribed.getPrice(); // booksubscribe에서 price를 받아와야할 것 같음
            
            if (point.getPoints() < price) {
                // 포인트 부족
                PointExpired expired = new PointExpired(point);
                expired.publishAfterCommit();
                return;
            }

            // 포인트 사용
            point.setPoints(point.getPoints() - price);
            point.setHistory(LocalDateTime.now());
            repository().save(point);

            PointUsed used = new PointUsed(point);
            used.publishAfterCommit();

         });


    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void pointManagement(PointPolicyCreated pointPolicyCreated) {

        StoredPointPolicy policy = new StoredPointPolicy();
        policy.setPolicyId(pointPolicyCreated.getId());
        policy.setName(pointPolicyCreated.getName());
        policy.setDescription(pointPolicyCreated.getDescription());
        policy.setPointType(pointPolicyCreated.getPointType());
        policy.setAmount(pointPolicyCreated.getAmount());
        policy.setIsActive(pointPolicyCreated.getIsActive());
        policy.setCreatedAt(pointPolicyCreated.getCreatedAt());

        System.out.println("👉 저장할 정책: " + policy);
        storedPointPolicyRepository().save(policy);
        System.out.println("✅ 정책 저장 완료");
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void pointManagement(PointPolicyUpdated pointPolicyUpdated) {

        storedPointPolicyRepository().findById(pointPolicyUpdated.getId()).ifPresent(policy -> {
            policy.setName(pointPolicyUpdated.getName());
            policy.setDescription(pointPolicyUpdated.getDescription());
            policy.setPointType(pointPolicyUpdated.getPointType());
            policy.setAmount(pointPolicyUpdated.getAmount());
            policy.setIsActive(pointPolicyUpdated.getIsActive());
            policy.setUpdatedAt(pointPolicyUpdated.getUpdatedAt());
            storedPointPolicyRepository().save(policy);
        });
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void pointManagement(PointPolicyDeleted pointPolicyDeleted) {

        storedPointPolicyRepository().deleteById(pointPolicyDeleted.getId());

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
