package aivle.domain;

import aivle.AdmintaskApplication;
import aivle.domain.PointPolicyCreated;
import aivle.domain.PointPolicyDeleted;
import aivle.domain.PointPolicyUpdated;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;


@Entity
@Table(name = "Pointpolicy_table")
@Data
//<<< DDD / Aggregate Root
public class Pointpolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private PointType pointType;

    private Integer amount;

    private Boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PostPersist
    public void onPostPersist() {
        PointPolicyCreated pointPolicyCreated = new PointPolicyCreated(this);
        pointPolicyCreated.publishAfterCommit();
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updatedAt = LocalDateTime.now();
        PointPolicyUpdated pointPolicyUpdated = new PointPolicyUpdated(this);
        pointPolicyUpdated.publishAfterCommit();
    }

    @PreRemove
    public void onPreRemove() {
        PointPolicyDeleted pointPolicyDeleted = new PointPolicyDeleted(this);
        pointPolicyDeleted.publishAfterCommit();
    }

    public static PointpolicyRepository repository() {
        PointpolicyRepository pointpolicyRepository = AdmintaskApplication.applicationContext.getBean(
            PointpolicyRepository.class
        );
        return pointpolicyRepository;
    }
}
//>>> DDD / Aggregate Root
