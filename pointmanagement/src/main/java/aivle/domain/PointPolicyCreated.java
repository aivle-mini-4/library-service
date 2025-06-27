package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class PointPolicyCreated extends AbstractEvent {

    private String id;
    private String name;
    private String description;
    private Object pointType;
    private Integer amount;
    private Boolean isActive;
    private Object createdAt;
}
