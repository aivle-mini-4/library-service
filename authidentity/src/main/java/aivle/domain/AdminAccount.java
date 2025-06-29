package aivle.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import aivle.AuthidentityApplication;
import lombok.Data;

@Entity
@Table(name = "AdminAccount_table")
@Data
//<<< DDD / Aggregate Root
public class AdminAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole roles = UserRole.ADMIN;
    private String createdAt;
    private String updatedAt;

    public static AdminAccountRepository repository() {
        AdminAccountRepository adminAccountRepository = AuthidentityApplication.applicationContext.getBean(
            AdminAccountRepository.class
        );
        return adminAccountRepository;
    }

    public void signup(SignupCommand signupCommand) {
        //implement business logic here:
        
        this.setEmail(signupCommand.getEmail());
        this.setPassword(signupCommand.getPassword());
        this.setCreatedAt(new Date().toString());
        this.setUpdatedAt(new Date().toString());
        
        repository().save(this);

        SignedUp signedUp = new SignedUp(this);
        signedUp.publishAfterCommit();
    }

    public void login(LoginCommand loginCommand) {
        //implement business logic here:

        Logged logged = new Logged(this);
        logged.publishAfterCommit();
    }


    public void logout(LogoutCommand logoutCommand) {
        //implement business logic here:

    }

}
//>>> DDD / Aggregate Root
