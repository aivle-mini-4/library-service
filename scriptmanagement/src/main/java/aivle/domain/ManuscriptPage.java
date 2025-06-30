package aivle.domain;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "ManuscriptPage_table")
@Data
@ToString
public class ManuscriptPage {

    @Id
    private Long id;
    private Long authorId;
    private String title;
    private String content;
    private LocalDateTime updatedAt;
}
