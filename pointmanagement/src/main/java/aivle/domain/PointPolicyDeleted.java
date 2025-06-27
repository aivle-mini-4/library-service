package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class PointPolicyDeleted extends AbstractEvent {

    private Long id;
}
