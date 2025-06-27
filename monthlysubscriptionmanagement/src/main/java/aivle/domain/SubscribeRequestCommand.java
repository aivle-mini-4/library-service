package aivle.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class SubscribeRequestCommand {

    private Long id;
    private String name;
    private Boolean isSubscribed;
    private Date updatedAt;
}
