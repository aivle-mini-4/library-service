package aivle.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "RegisteredBestsellerView_table")
@Data
public class RegisteredBestsellerView {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String authorId;
    private String coverImageUrl;
    private String title;
    private String summary;
    private String category;
    private Integer price;
    private String content;
    private Integer views;
}
