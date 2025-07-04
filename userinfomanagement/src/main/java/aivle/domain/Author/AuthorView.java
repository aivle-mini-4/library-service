package aivle.domain.Author;

import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "AuthorView_table")
@Data
public class AuthorView {

    @Id
    private Long id;
    private String name;
    private String email;
    private String basicInformation;
    private String selfIntroduction;
    private String portfolio;
}
