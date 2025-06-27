package aivle.domain;

import aivle.AuthidentityApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "UserAccount_table")
@Data
//<<< DDD / Aggregate Root
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;

    private String password;

    private String roles;

    private Date createdAt;

    private Date updatedAt;

    public static UserAccountRepository repository() {
        UserAccountRepository userAccountRepository = AuthidentityApplication.applicationContext.getBean(
            UserAccountRepository.class
        );
        return userAccountRepository;
    }

    //<<< Clean Arch / Port Method
    public void signup(SignupCommand signupCommand) {
        //implement business logic here:

        SignedUp signedUp = new SignedUp(this);
        signedUp.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void logout(LogoutCommand logoutCommand) {
        //implement business logic here:

        Loggedout loggedout = new Loggedout(this);
        loggedout.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void login(LoginCommand loginCommand) {
        //implement business logic here:

        Logged logged = new Logged(this);
        logged.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
