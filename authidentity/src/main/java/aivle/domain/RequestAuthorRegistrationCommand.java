package aivle.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class RequestAuthorRegistrationCommand {

    private String password;
    private String email;
    private String selfIntroduction;
    private String portfolio;
}
