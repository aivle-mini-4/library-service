package aivle.domain;

import java.util.*;
import lombok.Data;

// 비구독자

@Data
public class SubscribeBookCommand {

    private Long bookId;
    private Long userId;
    private Boolean isBookSubscribed;
    private Integer price;
}
