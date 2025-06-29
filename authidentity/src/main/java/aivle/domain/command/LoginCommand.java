package aivle.domain.command;

import lombok.Data;

@Data
public class LoginCommand {

    private String password;
    private String email;
}
