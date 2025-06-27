package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class AuthorApproved extends AbstractEvent {

    private Long id;
    private Long authorId;
    private Date resultAt;
    private String state;
}
