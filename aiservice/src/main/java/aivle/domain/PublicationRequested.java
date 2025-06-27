package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class PublicationRequested extends AbstractEvent {

    private Long id;
    private Long authorId;
    private String title;
    private String content;
    private String updatedAt;
}
