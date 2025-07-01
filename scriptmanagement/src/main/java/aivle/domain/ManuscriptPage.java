package aivle.domain;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ManuscriptPage_table")
@Data
public class ManuscriptPage {

    @Id
    private Long id;
    private Long authorId;
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String content;
    
    private LocalDateTime updatedAt;
}
