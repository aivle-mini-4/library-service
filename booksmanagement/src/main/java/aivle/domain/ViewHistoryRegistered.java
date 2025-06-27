package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class ViewHistoryRegistered extends AbstractEvent {

    private Long id;
    private Integer bookId;
    private Integer userId;
}
