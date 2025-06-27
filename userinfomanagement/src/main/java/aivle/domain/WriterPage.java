package aivle.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "WriterPage_table")
@Data
public class WriterPage {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String name;
    private String email;
    private String basicInformation;
    private String selfInformation;
    private String portfolio;
}
