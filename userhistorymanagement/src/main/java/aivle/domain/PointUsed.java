package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;

import java.time.LocalDateTime;
import java.util.*;
import lombok.*;

@Data
@ToString
public class PointUsed extends AbstractEvent {

    private Long id;
    private Long userId;
    private Integer points;
    private LocalDateTime history;
}
