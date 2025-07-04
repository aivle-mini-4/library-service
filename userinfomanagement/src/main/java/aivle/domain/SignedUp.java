package aivle.domain;

import aivle.infra.AbstractEvent;
import java.time.LocalDateTime;
import lombok.*;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class SignedUp extends AbstractEvent {

    private Long id;
    private String email;
    private String password;
    private String roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}