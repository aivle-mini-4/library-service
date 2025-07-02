package aivle.domain;

import java.util.*;
import lombok.Data;

// 월구독자

@Data
public class ViewBookCommand {

    private Long userId;
    private Boolean isBookSubscribed;
}
