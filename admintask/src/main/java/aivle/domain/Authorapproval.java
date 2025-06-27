package aivle.domain;

import aivle.AdmintaskApplication;
import aivle.domain.AuthorApproved;
import aivle.domain.AuthorRejected;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Authorapproval_table")
@Data
//<<< DDD / Aggregate Root
public class Authorapproval {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long authorid;

    private String state;

    private Date appliedAt;

    private Date resultAt;

    private Date rejectedAt;

    private Long adminId;

    private String reason;

    @PostPersist
    public void onPostPersist() {
        AuthorApproved authorApproved = new AuthorApproved(this);
        authorApproved.publishAfterCommit();

        AuthorRejected authorRejected = new AuthorRejected(this);
        authorRejected.publishAfterCommit();
    }

    public static AuthorapprovalRepository repository() {
        AuthorapprovalRepository authorapprovalRepository = AdmintaskApplication.applicationContext.getBean(
            AuthorapprovalRepository.class
        );
        return authorapprovalRepository;
    }

    //<<< Clean Arch / Port Method
    public static void authorRouting(
        AuthorRegistrationRequested authorRegistrationRequested
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        Authorapproval authorapproval = new Authorapproval();
        repository().save(authorapproval);

        */

        /** Example 2:  finding and process
        

        repository().findById(authorRegistrationRequested.get???()).ifPresent(authorapproval->{
            
            authorapproval // do something
            repository().save(authorapproval);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
