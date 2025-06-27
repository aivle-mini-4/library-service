package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class ReadyToPublish extends AbstractEvent {

    private Long id;
    private String authorId;
    private String coverImageUrl;
    private String title;
    private String summary;
    private String category;
    private String price;
    private String content;
    private String views;
}
