package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;

import java.time.LocalDateTime;
import java.util.*;
import lombok.*;

@Data
@ToString
public class MonthlyBookSubscribed extends AbstractEvent {

    private Long id;
    private Long bookId;
    private Long userId;
    private Integer price;
    private String bookName;
    private Boolean isBookSubscribed;
    private LocalDateTime updatedAt;
}
