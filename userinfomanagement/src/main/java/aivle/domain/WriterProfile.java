package aivle.domain;

import aivle.UserinfomanagementApplication;
import aivle.domain.WriterProfileCreated;
import aivle.domain.WriterProfileUpdated;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "WriterProfile_table")
@Data
//<<< DDD / Aggregate Root
public class WriterProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String email;

    private String roles;

    private String basicInformation;

    private String selfIntroduction;

    private String portfolio;

    private Date updatedAt;

    @PostPersist
    public void onPostPersist() {
        WriterProfileUpdated writerProfileUpdated = new WriterProfileUpdated(
            this
        );
        writerProfileUpdated.publishAfterCommit();

        WriterProfileCreated writerProfileCreated = new WriterProfileCreated(
            this
        );
        writerProfileCreated.publishAfterCommit();
    }

    public static WriterProfileRepository repository() {
        WriterProfileRepository writerProfileRepository = UserinfomanagementApplication.applicationContext.getBean(
            WriterProfileRepository.class
        );
        return writerProfileRepository;
    }

    //<<< Clean Arch / Port Method
    public static void notifyAuthorOnRegistrationApproval(
        AuthorApproved authorApproved
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        WriterProfile writerProfile = new WriterProfile();
        repository().save(writerProfile);

        */

        /** Example 2:  finding and process
        

        repository().findById(authorApproved.get???()).ifPresent(writerProfile->{
            
            writerProfile // do something
            repository().save(writerProfile);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void notifyAuthorOnRegistrationRejection(
        AuthorRejected authorRejected
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        WriterProfile writerProfile = new WriterProfile();
        repository().save(writerProfile);

        */

        /** Example 2:  finding and process
        

        repository().findById(authorRejected.get???()).ifPresent(writerProfile->{
            
            writerProfile // do something
            repository().save(writerProfile);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void createWriterProfile(AuthorApproved authorApproved) {
        //implement business logic here:

        /** Example 1:  new item 
        WriterProfile writerProfile = new WriterProfile();
        repository().save(writerProfile);

        WriterProfileCreated writerProfileCreated = new WriterProfileCreated(writerProfile);
        writerProfileCreated.publishAfterCommit();
        */

        /** Example 2:  finding and process
        

        repository().findById(authorApproved.get???()).ifPresent(writerProfile->{
            
            writerProfile // do something
            repository().save(writerProfile);

            WriterProfileCreated writerProfileCreated = new WriterProfileCreated(writerProfile);
            writerProfileCreated.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
