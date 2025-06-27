package aivle.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class UnsubscribeRequestCommand {

    private Long id;
    private String name;
    private Boolean isSubscribed;
}
