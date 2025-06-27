package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class AuthorRegistrationRequested extends AbstractEvent {

    private Long id;
    private String email;
    private String selfIntroduction;
    private String portfolio;
}
