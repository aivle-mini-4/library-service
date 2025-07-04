package aivle.domain;

import lombok.Data;

@Data
public class AuthorSignupEvent {
    private Long id;
    private String email;
    private String selfIntroduction;
    private String portfolio;
}