package aivle.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "RegisteredBookView_table")
@Data
public class RegisteredBookView {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String authorId;
    private String coverImageUrl;
    private String title;
    private String summary;
    private String category;
    private String price;
    private String content;
    private Integer views;
}
