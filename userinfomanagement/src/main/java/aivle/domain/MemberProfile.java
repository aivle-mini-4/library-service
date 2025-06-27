package aivle.domain;

import aivle.UserinfomanagementApplication;
import aivle.domain.MemberProfileCreated;
import aivle.domain.MemberProfileUpdated;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "MemberProfile_table")
@Data
//<<< DDD / Aggregate Root
public class MemberProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String email;

    private String roles;

    private String basicInformation;

    private Date updatedAt;

    @PostPersist
    public void onPostPersist() {
        MemberProfileUpdated memberProfileUpdated = new MemberProfileUpdated(
            this
        );
        memberProfileUpdated.publishAfterCommit();

        MemberProfileCreated memberProfileCreated = new MemberProfileCreated(
            this
        );
        memberProfileCreated.publishAfterCommit();
    }

    public static MemberProfileRepository repository() {
        MemberProfileRepository memberProfileRepository = UserinfomanagementApplication.applicationContext.getBean(
            MemberProfileRepository.class
        );
        return memberProfileRepository;
    }

    //<<< Clean Arch / Port Method
    public static void createMemberProfile(SignedUp signedUp) {
        //implement business logic here:

        /** Example 1:  new item 
        MemberProfile memberProfile = new MemberProfile();
        repository().save(memberProfile);

        MemberProfileCreated memberProfileCreated = new MemberProfileCreated(memberProfile);
        memberProfileCreated.publishAfterCommit();
        */

        /** Example 2:  finding and process
        

        repository().findById(signedUp.get???()).ifPresent(memberProfile->{
            
            memberProfile // do something
            repository().save(memberProfile);

            MemberProfileCreated memberProfileCreated = new MemberProfileCreated(memberProfile);
            memberProfileCreated.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
