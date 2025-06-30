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

    @Enumerated(EnumType.STRING)
    private ApprovalState state;

    private Date appliedAt;

    private Date resultAt;

    private Date rejectedAt;

    private Long adminId;

    private String reason;

    // @PostPersist
    // public void onPostPersist() {
    //     AuthorApproved authorApproved = new AuthorApproved(this);
    //     authorApproved.publishAfterCommit();

    //     AuthorRejected authorRejected = new AuthorRejected(this);
    //     authorRejected.publishAfterCommit();
    // }


    public void approve(Long authorId) {
    this.state = "APPROVED";
    this.authorid = authorId;
    this.resultAt = new Date();
    AuthorApproved event = new AuthorApproved(this);
    event.publishAfterCommit();
    }

    public void reject(Long authorId, String reason) {
        this.state = "REJECTED";
        this.authorId = authorId;
        this.reason = reason;
        this.rejectedAt = new Date();
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
        authorapproval.setAuthorid(authorRegistrationRequested.getAuthorId());
        authorapproval.setState("PENDING");
        authorapproval.setAppliedAt(new Date());
        // 필요하다면 추가 정보 세팅 (예: reason 등)

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
