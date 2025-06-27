package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class AuthorRejected extends AbstractEvent {

    private Long id;
    private String resultAt;
    private Long authorId;
    private String state;
}
