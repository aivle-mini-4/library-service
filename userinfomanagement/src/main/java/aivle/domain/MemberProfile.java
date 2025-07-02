package aivle.domain;

import aivle.UserinfomanagementApplication;
import aivle.domain.MemberProfileCreated;
import aivle.domain.MemberProfileUpdated;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "MemberProfile_table")
//<<< DDD / Aggregate Root
public class MemberProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private String roles;
    private String basicInformation;
    private String selfIntroduction;
    private Date updatedAt;

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRoles() { return roles; }
    public String getBasicInformation() { return basicInformation; }
    public String getSelfIntroduction() { return selfIntroduction; }
    public Date getUpdatedAt() { return updatedAt; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setRoles(String roles) { this.roles = roles; }
    public void setBasicInformation(String basicInformation) { this.basicInformation = basicInformation; }
    public void setSelfIntroduction(String selfIntroduction) { this.selfIntroduction = selfIntroduction; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    @PostPersist
    public void onPostPersist() {
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
        MemberProfile memberProfile = new MemberProfile();
        memberProfile.setName(signedUp.getName());
        memberProfile.setEmail(signedUp.getEmail());
        memberProfile.setRoles(signedUp.getRoles());
        memberProfile.setBasicInformation(signedUp.getBasicInformation());
        memberProfile.setUpdatedAt(new Date());

        repository().save(memberProfile);
        MemberProfileCreated memberProfileCreated = new MemberProfileCreated(memberProfile);
        memberProfileCreated.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

    //<<< Clean Arch / Port Method
    public void updateMemberProfile(String name, String email, String roles, String basicInformation, String selfIntroduction) {
        this.setName(name);
        this.setEmail(email);
        this.setRoles(roles);
        this.setBasicInformation(basicInformation);
        this.setSelfIntroduction(selfIntroduction);
        this.setUpdatedAt(new Date());
        
        repository().save(this);
        MemberProfileUpdated memberProfileUpdated = new MemberProfileUpdated(this);
        memberProfileUpdated.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

    @Override
    public String toString() {
        return "MemberProfile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", roles='" + roles + '\'' +
                ", basicInformation='" + basicInformation + '\'' +
                ", selfIntroduction='" + selfIntroduction + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
//>>> DDD / Aggregate Root
