package aivle.domain;

import aivle.ScriptmanagementApplication;
import java.time.LocalDateTime;
import javax.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.Data;

@Entity
@Table(name = "Manuscript_table")
@Data
public class Manuscript {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long authorId;

    private String title;

    private String content;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PostPersist
    public void onPostPersist() {
        ManuscriptCreated manuscriptCreated = new ManuscriptCreated(this);
        manuscriptCreated.publishAfterCommit();
    }

    @PostUpdate
    public void onPostUpdate() {
        ManuscriptUpdated manuscriptUpdated = new ManuscriptUpdated(this);
        manuscriptUpdated.publishAfterCommit();
    }

    @PreRemove
    public void onPreRemove() {
        ManuscriptDeleted manuscriptDeleted = new ManuscriptDeleted(this);
        manuscriptDeleted.publishAfterCommit();
    }

    public static ManuscriptRepository repository() {
        ManuscriptRepository manuscriptRepository = ScriptmanagementApplication.applicationContext.getBean(
            ManuscriptRepository.class
        );
        return manuscriptRepository;
    }
}
