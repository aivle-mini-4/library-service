package aivle.domain.command;

import lombok.Data;

@Data
public class SignupCommand {

    private String email;
    private String password;
}
