package aivle.domain;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "StoredPointPolicy_table")
@Data

public class StoredPointPolicy {
    @Id
    private Long policyId; // 정책 ID (String 타입)

    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private PointType pointType;
    private Integer amount;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
