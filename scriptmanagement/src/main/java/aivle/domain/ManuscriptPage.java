package aivle.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "ManuscriptPage_table")
@Data
public class ManuscriptPage {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private Long authorId;
    private String title;
    private String content;
    private Date updatedAt;
}
