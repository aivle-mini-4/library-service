package aivle.domain.command;

import lombok.Data;

@Data
public class RequestAuthorRegistrationCommand {

    private String password;
    private String email;
    private String selfIntroduction;
    private String portfolio;
}
