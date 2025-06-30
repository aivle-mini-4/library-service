package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class MonthlyBookSubscribed extends AbstractEvent {

    private Long id;
    private Long bookId;
    private String name;
    private Boolean isBookSubscribed;
    private Date updatedAt;
}
