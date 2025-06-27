package aivle.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class SignupCommand {

    private String email;
    private String password;
}
