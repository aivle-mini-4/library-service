package aivle.domain;

import aivle.UserinfomanagementApplication;
import aivle.domain.AdminProfileCreated;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
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

    public static AdminProfileRepository repository() {
        AdminProfileRepository adminProfileRepository = UserinfomanagementApplication.applicationContext.getBean(
            AdminProfileRepository.class
        );
        return adminProfileRepository;
    }

    //<<< Clean Arch / Port Method
    public static void createAdminProfile(SignedUp signedUp) {
        //implement business logic here:

        /** Example 1:  new item 
        AdminProfile adminProfile = new AdminProfile();
        repository().save(adminProfile);

        AdminProfileCreated adminProfileCreated = new AdminProfileCreated(adminProfile);
        adminProfileCreated.publishAfterCommit();
        */

        /** Example 2:  finding and process
        

        repository().findById(signedUp.get???()).ifPresent(adminProfile->{
            
            adminProfile // do something
            repository().save(adminProfile);

            AdminProfileCreated adminProfileCreated = new AdminProfileCreated(adminProfile);
            adminProfileCreated.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
