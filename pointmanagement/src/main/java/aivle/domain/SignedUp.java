package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class SignedUp extends AbstractEvent {

    private Long id;
    private String email;
    private String password;
    private String roles;
    private Date createdAt;
    private Date updatedAt;
}
