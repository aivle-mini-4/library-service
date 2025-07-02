package aivle.domain;

import aivle.UserinfomanagementApplication;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "AdminProfile_table")
@Data
//<<< DDD / Aggregate Root
public class AdminProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private String roles;
    private Date updatedAt;

    public static AdminProfileRepository repository() {
        AdminProfileRepository adminProfileRepository = UserinfomanagementApplication.applicationContext.getBean(
            AdminProfileRepository.class
        );
        return adminProfileRepository;
    }

    //<<< Clean Arch / Port Method
    public static void createAdminProfile(SignedUp signedUp) {
        AdminProfile adminProfile = new AdminProfile();
        adminProfile.setName(signedUp.getName());
        adminProfile.setEmail(signedUp.getEmail());
        adminProfile.setRoles(signedUp.getRoles());
        adminProfile.setUpdatedAt(new Date());
        repository().save(adminProfile);
        AdminProfileCreated adminProfileCreated = new AdminProfileCreated(adminProfile);
        adminProfileCreated.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method
}
//>>> DDD / Aggregate Root
