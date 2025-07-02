package aivle.domain;


import aivle.AdmintaskApplication;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Entity
@Table(name = "Authorapproval_table")
@Data
//<<< DDD / Aggregate Root
public class Authorapproval {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private Long authorId;


    @Enumerated(EnumType.STRING)
    private ApprovalState state;


    private LocalDateTime appliedAt;


    private LocalDateTime resultAt;


    private LocalDateTime rejectedAt;


    private Long adminId;


    private String reason;


    // @PostPersist
    // public void onPostPersist() {
    //     AuthorApproved authorApproved = new AuthorApproved(this);
    //     authorApproved.publishAfterCommit();


    //     AuthorRejected authorRejected = new AuthorRejected(this);
    //     authorRejected.publishAfterCommit();
    // }



    public void approve(Long adminId) {
    this.state = ApprovalState.APPROVED;
    this.adminId= adminId;
    this.resultAt = LocalDateTime.now();
    AuthorApproved event = new AuthorApproved(this);
    event.publishAfterCommit();
    }


    public void reject(Long adminId, String reason) {
        this.state = ApprovalState.REJECTED;
        this.adminId = adminId;
        this.reason = reason;
        this.rejectedAt = LocalDateTime.now();
        AuthorRejected event = new AuthorRejected(this);
        event.publishAfterCommit();
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
        // 1. 신규 작가 승인 요청 생성
        Authorapproval authorapproval = new Authorapproval();
        authorapproval.setAuthorId(authorRegistrationRequested.getAuthorId());
        authorapproval.setState(ApprovalState.PENDING);
        authorapproval.setAppliedAt(LocalDateTime.now());



        repository().save(authorapproval);



        // example2


        // repository().findById(authorRegistrationRequested.get???()).ifPresent(authorapproval->{
           
        //     authorapproval // do something
        //     repository().save(authorapproval);



        //  });



    }
    //>>> Clean Arch / Port Method


}
//>>> DDD / Aggregate Root