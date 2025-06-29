package aivle.domain.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import aivle.AuthidentityApplication;
import aivle.domain.command.LoginCommand;
import aivle.domain.command.LogoutCommand;
import aivle.domain.command.SignupCommand;
import aivle.domain.enums.UserRole;
import aivle.domain.event.Logged;
import aivle.domain.event.Loggedout;
import aivle.domain.event.SignedUp;
import aivle.domain.repository.UserAccountRepository;
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
    @Enumerated(EnumType.STRING)
    private UserRole roles = UserRole.USER;
    private Date createdAt;
    private Date updatedAt;

    public static UserAccountRepository repository() {
        UserAccountRepository userAccountRepository = AuthidentityApplication.applicationContext.getBean(
            UserAccountRepository.class
        );
        return userAccountRepository;
    }

    public void signup(SignupCommand signupCommand) {
        //implement business logic here:
        
        this.setEmail(signupCommand.getEmail());
        this.setPassword(signupCommand.getPassword());
        this.setCreatedAt(new Date());
        this.setUpdatedAt(new Date());
        
        repository().save(this);

        SignedUp signedUp = new SignedUp(this);
        signedUp.publishAfterCommit();
    }


    public void logout(LogoutCommand logoutCommand) {
        //implement business logic here:

        Loggedout loggedout = new Loggedout(this);
        loggedout.publishAfterCommit();
    }


    public void login(LoginCommand loginCommand) {
        //implement business logic here:

        Logged logged = new Logged(this);
        logged.publishAfterCommit();
    }

}
//>>> DDD / Aggregate Root
