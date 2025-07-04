package aivle.domain;

import aivle.infra.AbstractEvent;
import java.time.LocalDateTime;
import lombok.*;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class AuthorApproved extends AbstractEvent {
    private Long id;
    private Long adminId;
    private Long authorId;
    private LocalDateTime resultAt;
    private ApprovalState state;

    public AuthorApproved() {
        super();
    }
}