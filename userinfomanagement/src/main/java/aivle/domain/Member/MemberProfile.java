package aivle.domain.Member;

import aivle.UserinfomanagementApplication;
import aivle.domain.SignedUp;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "MemberProfile_table")
@Data
//<<< DDD / Aggregate Root
public class MemberProfile {

    @Id
    private Long id;
    private String name;
    private String email;
    private String roles;
    private String basicInformation;
    private LocalDateTime updatedAt;

    @PostPersist
    public void onPostPersist() {
        MemberProfileCreated event = new MemberProfileCreated(this);
        event.publishAfterCommit();
    }

    public static MemberProfileRepository repository() {
        return UserinfomanagementApplication.applicationContext.getBean(
            MemberProfileRepository.class
        );
    }

    //<<< Clean Arch / Port Method
    public static void createMemberProfile(SignedUp signedUp) {
        MemberProfile member = new MemberProfile();
        member.setId(signedUp.getId());
        member.setName(null);
        member.setEmail(signedUp.getEmail());
        member.setRoles(signedUp.getRoles());
        member.setBasicInformation(null);
        member.setUpdatedAt(LocalDateTime.now());

        repository().save(member);
        MemberProfileCreated event = new MemberProfileCreated(member);
        event.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

    //<<< Clean Arch / Port Method
    public void updateMemberProfile(String name, String email, String basicInformation) {
        this.setName(name);
        this.setEmail(email);
        this.setBasicInformation(basicInformation);
        this.setUpdatedAt(LocalDateTime.now());
        
        repository().save(this);
        MemberProfileUpdated event = new MemberProfileUpdated(this);
        event.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method
}
//>>> DDD / Aggregate Root
