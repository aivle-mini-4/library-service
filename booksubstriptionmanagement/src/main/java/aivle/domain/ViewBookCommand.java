package aivle.domain;

import java.util.*;
import lombok.Data;

// 월구독자

@Data
public class ViewBookCommand {

    private Long userId;
    private Long bookId;
    private Boolean isBookSubscribed;
    private Integer price;
}
