package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;

import java.time.LocalDateTime;
import java.util.*;
import lombok.*;

@Data
@ToString
public class PointPolicyCreated extends AbstractEvent {

    private Long id;
    private String name;
    private String description;
    private PointType pointType;
    private Integer amount;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
