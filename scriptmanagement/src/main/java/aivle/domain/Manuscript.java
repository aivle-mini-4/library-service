package aivle.domain;

import aivle.ScriptmanagementApplication;
import aivle.domain.ManuscriptCreated;
import aivle.domain.ManuscriptDeleted;
import aivle.domain.ManuscriptUpdated;
import aivle.domain.PublicationRequested;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Manuscript_table")
@Data
//<<< DDD / Aggregate Root
public class Manuscript {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long authorId;

    private String title;

    private String content;

    private Date updatedAt;

    @PostPersist
    public void onPostPersist() {
        ManuscriptCreated manuscriptCreated = new ManuscriptCreated(this);
        manuscriptCreated.publishAfterCommit();

        PublicationRequested publicationRequested = new PublicationRequested(
            this
        );
        publicationRequested.publishAfterCommit();
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
//>>> DDD / Aggregate Root
